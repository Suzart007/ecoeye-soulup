import { useEffect, useRef, type ReactNode } from 'react'
import Button from '../common/Button'
import { AlertIcon } from '../icons/Icons'

interface ConfirmDialogProps {
  isOpen: boolean
  title: string
  description: ReactNode
  confirmLabel: string
  isProcessing?: boolean
  onConfirm: () => void
  onCancel: () => void
}

/** Modal de confirmação para ações irreversíveis (ex.: excluir um registro na API). */
export default function ConfirmDialog({
  isOpen,
  title,
  description,
  confirmLabel,
  isProcessing = false,
  onConfirm,
  onCancel,
}: ConfirmDialogProps) {
  const cancelRef = useRef<HTMLButtonElement>(null)

  useEffect(() => {
    if (isOpen) cancelRef.current?.focus()
  }, [isOpen])

  useEffect(() => {
    if (!isOpen) return

    const handleKeyDown = (event: KeyboardEvent) => {
      if (event.key === 'Escape' && !isProcessing) onCancel()
    }
    document.addEventListener('keydown', handleKeyDown)
    return () => document.removeEventListener('keydown', handleKeyDown)
  }, [isOpen, isProcessing, onCancel])

  if (!isOpen) return null

  return (
    <div className="fixed inset-0 z-[70] flex items-end sm:items-center justify-center bg-text-dark/50 p-4">
      <div
        role="alertdialog"
        aria-modal="true"
        aria-labelledby="confirm-dialog-title"
        aria-describedby="confirm-dialog-description"
        className="w-full max-w-md rounded-2xl bg-white shadow-lg p-6"
      >
        <div className="flex items-start gap-4 mb-6">
          <span className="w-11 h-11 rounded-xl bg-danger/10 text-danger flex items-center justify-center flex-shrink-0">
            <AlertIcon className="w-5 h-5" />
          </span>
          <div>
            <h2 id="confirm-dialog-title" className="text-lg font-bold text-text-dark mb-1">
              {title}
            </h2>
            <div id="confirm-dialog-description" className="text-sm text-text leading-relaxed">
              {description}
            </div>
          </div>
        </div>

        <div className="flex flex-col-reverse sm:flex-row sm:justify-end gap-3">
          <button
            ref={cancelRef}
            type="button"
            onClick={onCancel}
            disabled={isProcessing}
            className="inline-flex items-center justify-center rounded-full border border-border px-6 py-3 text-sm font-semibold text-text-dark hover:border-primary-light disabled:opacity-50"
          >
            Cancelar
          </button>
          <Button variant="danger" onClick={onConfirm} disabled={isProcessing}>
            {isProcessing ? 'Excluindo...' : confirmLabel}
          </Button>
        </div>
      </div>
    </div>
  )
}
