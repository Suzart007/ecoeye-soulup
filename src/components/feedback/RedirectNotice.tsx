import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useDocumentTitle } from '../../hooks/useDocumentTitle'
import Button from '../common/Button'
import { SearchIcon } from '../icons/Icons'

interface RedirectNoticeProps {
  title: string
  message: string
  to: string
  linkLabel: string
  seconds?: number
}

/** Avisa que o registro não existe e redireciona o usuário após uma contagem regressiva. */
export default function RedirectNotice({ title, message, to, linkLabel, seconds = 5 }: RedirectNoticeProps) {
  const navigate = useNavigate()
  const [remaining, setRemaining] = useState(seconds)
  useDocumentTitle(title)

  useEffect(() => {
    if (remaining <= 0) {
      navigate(to, { replace: true })
      return
    }
    const timeout = setTimeout(() => setRemaining((current) => current - 1), 1000)
    return () => clearTimeout(timeout)
  }, [remaining, navigate, to])

  return (
    <section className="py-20 md:py-24 px-4 text-center" role="alert">
      <div className="w-14 h-14 rounded-2xl bg-bg-soft text-primary flex items-center justify-center mx-auto mb-4">
        <SearchIcon className="w-7 h-7" />
      </div>
      <h1 className="text-2xl md:text-3xl font-bold text-text-dark mb-2">{title}</h1>
      <p className="text-text max-w-md mx-auto mb-2">{message}</p>
      <p className="text-sm text-text-muted mb-8" aria-live="polite">
        Redirecionando em {remaining} {remaining === 1 ? 'segundo' : 'segundos'}...
      </p>
      <Button to={to} variant="secondary">
        {linkLabel}
      </Button>
    </section>
  )
}
