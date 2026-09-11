// commit 13/29 (Guilherme): feat(contato) - implementa formulário de contato com React Hook Form, validações e máscara de telefone
import { useState } from 'react'
import { useForm } from 'react-hook-form'
import type { ContactFormData } from '../../types'
import { CheckIcon, SendIcon, XCircleIcon } from '../icons/Icons'

function applyPhoneMask(value: string): string {
  const digits = value.replace(/\D/g, '').slice(0, 11)
  if (digits.length <= 10) {
    return digits.replace(/(\d{0,2})(\d{0,4})(\d{0,4}).*/, (_match, a, b, c) => {
      if (b && c) return `(${a}) ${b}-${c}`
      if (b) return `(${a}) ${b}`
      if (a) return `(${a}`
      return ''
    })
  }
  return digits.replace(/(\d{0,2})(\d{0,5})(\d{0,4}).*/, (_match, a, b, c) => {
    if (b && c) return `(${a}) ${b}-${c}`
    if (b) return `(${a}) ${b}`
    if (a) return `(${a}`
    return ''
  })
}

const inputClasses =
  'w-full rounded-xl border px-4 py-2.5 text-sm bg-white outline-none transition-colors focus:border-primary'
const errorInputClasses = 'border-danger'
const validInputClasses = 'border-border'

export default function ContactForm() {
  const [isSubmitted, setIsSubmitted] = useState(false)

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors, isSubmitting },
  } = useForm<ContactFormData>({ mode: 'onBlur' })

  const { onChange: onTelefoneChange, ...telefoneField } = register('telefone', {
    validate: (value) => {
      if (!value) return true
      const digits = value.replace(/\D/g, '')
      return (digits.length >= 10 && digits.length <= 11) || 'O telefone deve ter entre 10 e 11 dígitos.'
    },
  })

  const onSubmit = async () => {
    await new Promise((resolve) => setTimeout(resolve, 800))
    setIsSubmitted(true)
    reset()
  }

  return (
    <div className="bg-white rounded-2xl border border-border p-6 md:p-8">
      <h2 className="text-xl font-bold text-text-dark mb-1">Envie sua mensagem</h2>
      <p className="text-sm text-text-muted mb-6">Preencha o formulário abaixo e nossa equipe entrará em contato.</p>

      <form onSubmit={handleSubmit(onSubmit)} noValidate className="space-y-5">
        <div className="grid sm:grid-cols-2 gap-4">
          <div>
            <label htmlFor="nome" className="block text-sm font-medium text-text-dark mb-1.5">
              Nome completo <span className="text-danger">*</span>
            </label>
            <input
              id="nome"
              type="text"
              autoComplete="name"
              placeholder="Seu nome"
              aria-invalid={!!errors.nome}
              className={`${inputClasses} ${errors.nome ? errorInputClasses : validInputClasses}`}
              {...register('nome', {
                required: 'Informe seu nome completo (mínimo 3 caracteres).',
                minLength: { value: 3, message: 'Informe seu nome completo (mínimo 3 caracteres).' },
                maxLength: { value: 80, message: 'O nome deve ter no máximo 80 caracteres.' },
              })}
            />
            {errors.nome && <p className="text-xs text-danger mt-1">{errors.nome.message}</p>}
          </div>

          <div>
            <label htmlFor="email" className="block text-sm font-medium text-text-dark mb-1.5">
              E-mail <span className="text-danger">*</span>
            </label>
            <input
              id="email"
              type="email"
              autoComplete="email"
              placeholder="voce@email.com"
              aria-invalid={!!errors.email}
              className={`${inputClasses} ${errors.email ? errorInputClasses : validInputClasses}`}
              {...register('email', {
                required: 'O e-mail é obrigatório.',
                pattern: {
                  value: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
                  message: 'Informe um e-mail válido (exemplo: voce@email.com).',
                },
              })}
            />
            {errors.email && <p className="text-xs text-danger mt-1">{errors.email.message}</p>}
          </div>
        </div>

        <div className="grid sm:grid-cols-2 gap-4">
          <div>
            <label htmlFor="telefone" className="block text-sm font-medium text-text-dark mb-1.5">
              Telefone (opcional)
            </label>
            <input
              id="telefone"
              type="tel"
              autoComplete="tel"
              placeholder="(11) 99999-9999"
              maxLength={15}
              aria-invalid={!!errors.telefone}
              className={`${inputClasses} ${errors.telefone ? errorInputClasses : validInputClasses}`}
              onChange={(event) => {
                event.target.value = applyPhoneMask(event.target.value)
                onTelefoneChange(event)
              }}
              {...telefoneField}
            />
            {errors.telefone ? (
              <p className="text-xs text-danger mt-1">{errors.telefone.message}</p>
            ) : (
              <p className="text-xs text-text-muted mt-1">Apenas se preferir contato por telefone.</p>
            )}
          </div>

          <div>
            <label htmlFor="assunto" className="block text-sm font-medium text-text-dark mb-1.5">
              Assunto <span className="text-danger">*</span>
            </label>
            <select
              id="assunto"
              defaultValue=""
              aria-invalid={!!errors.assunto}
              className={`${inputClasses} ${errors.assunto ? errorInputClasses : validInputClasses}`}
              {...register('assunto', { required: 'Selecione o assunto da sua mensagem.' })}
            >
              <option value="" disabled>
                Selecione...
              </option>
              <option value="duvida">Dúvida sobre a plataforma</option>
              <option value="suporte">Suporte técnico</option>
              <option value="parceria">Proposta de parceria</option>
              <option value="imprensa">Imprensa / Mídia</option>
              <option value="outro">Outro assunto</option>
            </select>
            {errors.assunto && <p className="text-xs text-danger mt-1">{errors.assunto.message}</p>}
          </div>
        </div>

        <div>
          <label htmlFor="mensagem" className="block text-sm font-medium text-text-dark mb-1.5">
            Mensagem <span className="text-danger">*</span>
          </label>
          <textarea
            id="mensagem"
            rows={5}
            placeholder="Conte para a gente como podemos ajudar..."
            aria-invalid={!!errors.mensagem}
            className={`${inputClasses} resize-none ${errors.mensagem ? errorInputClasses : validInputClasses}`}
            {...register('mensagem', {
              required: 'A mensagem deve ter pelo menos 10 caracteres.',
              minLength: { value: 10, message: 'A mensagem deve ter pelo menos 10 caracteres.' },
              maxLength: { value: 1000, message: 'A mensagem deve ter no máximo 1000 caracteres.' },
            })}
          />
          {errors.mensagem ? (
            <p className="text-xs text-danger mt-1">{errors.mensagem.message}</p>
          ) : (
            <p className="text-xs text-text-muted mt-1">Mínimo de 10 caracteres. Máximo de 1000.</p>
          )}
        </div>

        <div>
          <label className="flex items-start gap-3 text-sm text-text">
            <input
              type="checkbox"
              className="mt-0.5 w-4 h-4 accent-primary"
              aria-invalid={!!errors.privacidade}
              {...register('privacidade', { required: 'Você precisa concordar com nossa política de privacidade.' })}
            />
            <span>
              Concordo em compartilhar meus dados conforme a política de privacidade e a LGPD.{' '}
              <span className="text-danger">*</span>
            </span>
          </label>
          {errors.privacidade && <p className="text-xs text-danger mt-1">{errors.privacidade.message}</p>}
        </div>

        {isSubmitted && (
          <div className="flex items-center gap-2 rounded-xl bg-success/10 text-success text-sm font-medium px-4 py-3" role="status" aria-live="polite">
            <CheckIcon className="w-4 h-4 flex-shrink-0" />
            Mensagem enviada com sucesso! Em breve nossa equipe entrará em contato.
          </div>
        )}

        {Object.keys(errors).length > 0 && !isSubmitted && (
          <div className="flex items-center gap-2 rounded-xl bg-danger/10 text-danger text-sm font-medium px-4 py-3" role="alert">
            <XCircleIcon className="w-4 h-4 flex-shrink-0" />
            Verifique os campos destacados e tente novamente.
          </div>
        )}

        <button
          type="submit"
          disabled={isSubmitting}
          className="inline-flex items-center justify-center gap-2 rounded-full bg-primary text-white font-semibold text-sm px-6 py-3 hover:bg-primary-dark transition-colors disabled:opacity-60"
        >
          {isSubmitting ? 'Enviando...' : 'Enviar mensagem'}
          <SendIcon />
        </button>
      </form>
    </div>
  )
}
