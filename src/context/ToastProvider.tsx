import { useCallback, useMemo, useRef, useState, type ReactNode } from 'react'
import { ToastContext, type Toast, type ToastTone } from './toast-context'
import { CheckIcon, InfoIcon, XCircleIcon } from '../components/icons/Icons'

const TOAST_DURATION_MS = 5000

const toneClasses: Record<ToastTone, string> = {
  success: 'border-success text-success',
  error: 'border-danger text-danger',
  info: 'border-info text-info',
}

const toneIcons: Record<ToastTone, ReactNode> = {
  success: <CheckIcon className="w-5 h-5" />,
  error: <XCircleIcon className="w-5 h-5" />,
  info: <InfoIcon className="w-5 h-5" />,
}

/** Exibe notificações rápidas (sucesso, erro, aviso) disparadas por qualquer página via useToast(). */
export function ToastProvider({ children }: { children: ReactNode }) {
  const [toasts, setToasts] = useState<Toast[]>([])
  const nextId = useRef(0)

  const dismiss = useCallback((id: number) => {
    setToasts((current) => current.filter((toast) => toast.id !== id))
  }, [])

  const showToast = useCallback(
    (message: string, tone: ToastTone = 'success') => {
      nextId.current += 1
      const id = nextId.current
      setToasts((current) => [...current, { id, tone, message }])
      setTimeout(() => dismiss(id), TOAST_DURATION_MS)
    },
    [dismiss],
  )

  const value = useMemo(() => ({ showToast }), [showToast])

  return (
    <ToastContext.Provider value={value}>
      {children}

      <div
        aria-live="polite"
        className="fixed z-[60] bottom-20 md:bottom-24 inset-x-4 sm:inset-x-auto sm:right-6 sm:w-96 flex flex-col gap-3 pointer-events-none"
      >
        {toasts.map((toast) => (
          <div
            key={toast.id}
            role={toast.tone === 'error' ? 'alert' : 'status'}
            className={`pointer-events-auto flex items-start gap-3 rounded-xl border-l-4 bg-white shadow-lg px-4 py-3 ${toneClasses[toast.tone]}`}
          >
            <span className="flex-shrink-0 mt-0.5">{toneIcons[toast.tone]}</span>
            <p className="flex-1 text-sm font-medium text-text-dark leading-snug">{toast.message}</p>
            <button
              type="button"
              onClick={() => dismiss(toast.id)}
              aria-label="Fechar notificação"
              className="text-text-muted hover:text-text-dark text-lg leading-none px-1"
            >
              &times;
            </button>
          </div>
        ))}
      </div>
    </ToastContext.Provider>
  )
}
