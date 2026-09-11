// commit 10/29 (Guilherme): feat(routes) - implementa roteamento da SPA com React Router DOM
import { useEffect } from 'react'
import { useLocation } from 'react-router-dom'

/** Rola a página para o topo sempre que a rota muda (o React Router não faz isso sozinho). */
export default function ScrollToTop() {
  const { pathname } = useLocation()

  useEffect(() => {
    window.scrollTo(0, 0)
  }, [pathname])

  return null
}
