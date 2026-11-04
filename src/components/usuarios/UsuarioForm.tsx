import { useForm } from 'react-hook-form'
import Button from '../common/Button'
import FormField, { inputClassName } from '../form/FormField'
import { XCircleIcon } from '../icons/Icons'
import { SITUACOES_USUARIO, SITUACAO_USUARIO_INFO } from '../../data/situacoes'
import { ApiError } from '../../services/api'
import type { UsuarioPayload } from '../../types/api'

/** Campos editáveis pelo formulário; a data de cadastro é controlada pela página. */
export type UsuarioFormValues = Omit<UsuarioPayload, 'dataCadastro'>

const EMPTY_USUARIO: UsuarioFormValues = { nome: '', email: '', situacao: 'ATIVO' }

interface UsuarioFormProps {
  defaultValues?: UsuarioFormValues
  submitLabel: string
  cancelTo: string
  onSubmit: (values: UsuarioFormValues) => Promise<void>
}

export default function UsuarioForm({ defaultValues = EMPTY_USUARIO, submitLabel, cancelTo, onSubmit }: UsuarioFormProps) {
  const {
    register,
    handleSubmit,
    setError,
    watch,
    formState: { errors, isSubmitting },
  } = useForm<UsuarioFormValues>({ mode: 'onBlur', defaultValues })

  const situacaoSelecionada = watch('situacao')

  const submit = async (values: UsuarioFormValues) => {
    try {
      await onSubmit({ ...values, nome: values.nome.trim(), email: values.email.trim().toLowerCase() })
    } catch (error) {
      setError('root.server', { message: ApiError.from(error).message })
    }
  }

  return (
    <form onSubmit={handleSubmit(submit)} noValidate className="space-y-5">
      <FormField htmlFor="nome" label="Nome completo" required error={errors.nome?.message}>
        <input
          id="nome"
          type="text"
          autoComplete="name"
          placeholder="Ex.: Ana Beatriz Lima"
          aria-invalid={!!errors.nome}
          aria-describedby={errors.nome ? 'nome-error' : undefined}
          className={inputClassName(!!errors.nome)}
          {...register('nome', {
            validate: (value) => {
              const length = value.trim().length
              if (length < 3) return 'Informe o nome completo (mínimo 3 caracteres).'
              return length <= 120 || 'O nome deve ter no máximo 120 caracteres.'
            },
          })}
        />
      </FormField>

      <FormField htmlFor="email" label="E-mail" required error={errors.email?.message} hint="Cada e-mail só pode ser usado por um usuário.">
        <input
          id="email"
          type="email"
          autoComplete="email"
          placeholder="voce@email.com"
          aria-invalid={!!errors.email}
          aria-describedby={errors.email ? 'email-error' : undefined}
          className={inputClassName(!!errors.email)}
          {...register('email', {
            required: 'O e-mail é obrigatório.',
            maxLength: { value: 150, message: 'O e-mail deve ter no máximo 150 caracteres.' },
            pattern: {
              value: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
              message: 'Informe um e-mail válido (exemplo: voce@email.com).',
            },
          })}
        />
      </FormField>

      <FormField
        htmlFor="situacao"
        label="Situação da conta"
        required
        error={errors.situacao?.message}
        hint={SITUACAO_USUARIO_INFO[situacaoSelecionada]?.description}
      >
        <select
          id="situacao"
          aria-invalid={!!errors.situacao}
          className={inputClassName(!!errors.situacao)}
          {...register('situacao', { required: 'Selecione a situação do usuário.' })}
        >
          {SITUACOES_USUARIO.map((situacao) => (
            <option key={situacao} value={situacao}>
              {SITUACAO_USUARIO_INFO[situacao].label}
            </option>
          ))}
        </select>
      </FormField>

      {errors.root?.server && (
        <div role="alert" className="flex items-start gap-2 rounded-xl bg-danger/10 text-danger text-sm font-medium px-4 py-3">
          <XCircleIcon className="w-4 h-4 flex-shrink-0 mt-0.5" />
          {errors.root.server.message}
        </div>
      )}

      <div className="flex flex-col-reverse sm:flex-row sm:justify-end gap-3 pt-2">
        <Button to={cancelTo} variant="secondary">
          Cancelar
        </Button>
        <Button type="submit" disabled={isSubmitting}>
          {isSubmitting ? 'Salvando...' : submitLabel}
        </Button>
      </div>
    </form>
  )
}
