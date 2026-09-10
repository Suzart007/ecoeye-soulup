// commit 07/29 (Guilherme): feat(hooks) - adiciona hooks de título de página e scroll reveal
import { useEffect } from 'react'

export function useDocumentTitle(title: string) {
  useEffect(() => {
    const previousTitle = document.title
    document.title = `${title} — SoulUp`
    return () => {
      document.title = previousTitle
    }
  }, [title])
}
