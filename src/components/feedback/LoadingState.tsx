interface LoadingStateProps {
  message: string
}

export default function LoadingState({ message }: LoadingStateProps) {
  return (
    <div role="status" aria-live="polite" className="flex flex-col items-center justify-center gap-4 py-16 md:py-24 text-center">
      <span className="w-10 h-10 rounded-full border-4 border-bg-soft border-t-primary animate-spin" aria-hidden="true" />
      <p className="text-sm text-text-muted">{message}</p>
    </div>
  )
}
