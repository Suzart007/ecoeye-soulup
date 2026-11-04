import { useForm } from 'react-hook-form'
import Button from '../common/Button'
import FormField, { inputClassName } from '../form/FormField'
import { XCircleIcon } from '../icons/Icons'
import { SITUACOES_MISSAO, SITUACAO_MISSAO_INFO } from '../../data/situacoes'
import { ApiError } from '../../services/api'
import type { MissaoPayload } from '../../types/api'

const EMPTY_MISSAO: MissaoPayload = { titulo: '', descricao: '', pontuacao: 30, situacao: 'ATIVA' }

interface MissaoFormProps {
  defaultValues?: MissaoPayload
  submitLabel: string
  cancelTo: string
  onSubmit: (payload: MissaoPayload) => Promise<void>
}

/** Formulário de cadastro/edição de missão. As regras espelham os CHECKs da TB_MISSOES. */
export default function MissaoForm({ defaultValues = EMPTY_MISSAO, submitLabel, cancelTo, onSubmit }: MissaoFormProps) {
  const {
    register,
    handleSubmit,
    setError,
    watch,
    formState: { errors, isSubmitting },
  } = useForm<MissaoPayload>({ mode: 'onBlur', defaultValues })

  const descricaoLength = (watch('descricao') ?? '').length

  const submit = async (values: MissaoPayload) => {
    try {
      await onSubmit({ ...values, titulo: values.titulo.trim(), descricao: values.descricao.trim() })
    } catch (error) {
      setError('root.server', { message: ApiError.from(error).message })
    }
  }

  return (
    <form onSubmit={handleSubmit(submit)} noValidate className="space-y-5">
      <FormField htmlFor="titulo" label="Título da missão" required error={errors.titulo?.message} hint="Entre 3 e 120 caracteres.">
        <input
          id="titulo"
          type="text"
          placeholder="Ex.: Banho Consciente"
          aria-invalid={!!errors.titulo}
          aria-describedby={errors.titulo ? 'titulo-error' : undefined}
          className={inputClassName(!!errors.titulo)}
          {...register('titulo', {
            validate: (value) => {
              const length = value.trim().length
              if (length < 3) return 'Informe um título com pelo menos 3 caracteres.'
              return length <= 120 || 'O título deve ter no máximo 120 caracteres.'
            },
          })}
        />
      </FormField>

      <FormField
        htmlFor="descricao"
        label="Descrição"
        required
        error={errors.descricao?.message}
        hint={`${descricaoLength}/255 caracteres. Explique o que o usuário precisa fazer.`}
      >
        <textarea
          id="descricao"
          rows={4}
          placeholder="Ex.: Reduzir o tempo de banho para no máximo 5 minutos durante 20 dias."
          aria-invalid={!!errors.descricao}
          aria-describedby={errors.descricao ? 'descricao-error' : undefined}
          className={`${inputClassName(!!errors.descricao)} resize-none`}
          {...register('descricao', {
            validate: (value) => {
              const length = value.trim().length
              if (length < 10) return 'A descrição deve ter pelo menos 10 caracteres.'
              return length <= 255 || 'A descrição deve ter no máximo 255 caracteres.'
            },
          })}
        />
      </FormField>

      <div className="grid sm:grid-cols-2 gap-4">
        <FormField htmlFor="pontuacao" label="Pontuação" required error={errors.pontuacao?.message} hint="Número inteiro de 0 a 100.">
          <input
            id="pontuacao"
            type="number"
            inputMode="numeric"
            min={0}
            max={100}
            step={1}
            aria-invalid={!!errors.pontuacao}
            aria-describedby={errors.pontuacao ? 'pontuacao-error' : undefined}
            className={inputClassName(!!errors.pontuacao)}
            {...register('pontuacao', {
              valueAsNumber: true,
              validate: (value) =>
                (Number.isInteger(value) && value >= 0 && value <= 100) || 'Informe um número inteiro entre 0 e 100.',
            })}
          />
        </FormField>

        <FormField htmlFor="situacao" label="Situação" required error={errors.situacao?.message}>
          <select
            id="situacao"
            aria-invalid={!!errors.situacao}
            className={inputClassName(!!errors.situacao)}
            {...register('situacao', { required: 'Selecione a situação da missão.' })}
          >
            {SITUACOES_MISSAO.map((situacao) => (
              <option key={situacao} value={situacao}>
                {SITUACAO_MISSAO_INFO[situacao].label}
              </option>
            ))}
          </select>
        </FormField>
      </div>

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
