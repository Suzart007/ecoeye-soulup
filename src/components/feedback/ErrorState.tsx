import type { ReactNode } from 'react'
import Button from '../common/Button'
import { AlertIcon, RefreshIcon } from '../icons/Icons'

interface ErrorStateProps {
  title: string
  message: string
  onRetry?: () => void
  children?: ReactNode
}

export default function ErrorState({ title, message, onRetry, children }: ErrorStateProps) {
  return (
    <div role="alert" className="max-w-xl mx-auto rounded-2xl border border-danger/30 bg-danger/5 p-6 md:p-8 text-center">
      <AlertIcon className="w-10 h-10 text-danger mx-auto mb-3" />
      <h2 className="text-lg md:text-xl font-bold text-text-dark mb-2">{title}</h2>
      <p className="text-sm text-text leading-relaxed mb-6">{message}</p>
      <div className="flex flex-col sm:flex-row justify-center gap-3">
        {onRetry && (
          <Button onClick={onRetry}>
            <RefreshIcon />
            Tentar novamente
          </Button>
        )}
        {children}
      </div>
    </div>
  )
}
