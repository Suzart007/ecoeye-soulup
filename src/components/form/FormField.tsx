import type { ReactNode } from 'react'

const baseInputClasses =
  'w-full rounded-xl border px-4 py-2.5 text-sm bg-white outline-none transition-colors focus:border-primary'

/** Classes do input conforme o estado de validação (mesmo visual do formulário de contato). */
export function inputClassName(hasError: boolean): string {
  return `${baseInputClasses} ${hasError ? 'border-danger' : 'border-border'}`
}

interface FormFieldProps {
  htmlFor: string
  label: string
  required?: boolean
  error?: string
  hint?: string
  children: ReactNode
}

export default function FormField({ htmlFor, label, required = false, error, hint, children }: FormFieldProps) {
  return (
    <div>
      <label htmlFor={htmlFor} className="block text-sm font-medium text-text-dark mb-1.5">
        {label} {required && <span className="text-danger">*</span>}
      </label>
      {children}
      {error ? (
        <p id={`${htmlFor}-error`} className="text-xs text-danger mt-1">
          {error}
        </p>
      ) : (
        hint && <p className="text-xs text-text-muted mt-1">{hint}</p>
      )}
    </div>
  )
}
