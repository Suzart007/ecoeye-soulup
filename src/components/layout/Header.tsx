// commit 09/29 (Guilherme): feat(layout) - cria Header responsivo com menu mobile e navegação ativa
import { useEffect, useState } from 'react'
import { Link, NavLink, useLocation } from 'react-router-dom'
import { NAV_ITEMS } from '../../data/navigation'
import { LeafIcon } from '../icons/Icons'

export default function Header() {
  const [isMenuOpen, setIsMenuOpen] = useState(false)
  const location = useLocation()

  useEffect(() => {
    setIsMenuOpen(false)
  }, [location.pathname])

  return (
    <header className="sticky top-0 z-40 bg-white/95 backdrop-blur border-b border-border">
      <div className="max-w-7xl mx-auto px-4 md:px-8 h-20 flex items-center justify-between">
        <Link to="/" className="flex items-center gap-2 font-heading font-bold text-xl text-text-dark" aria-label="SoulUp - Página inicial">
          <span className="w-9 h-9 rounded-full bg-bg-soft text-primary flex items-center justify-center">
            <LeafIcon className="w-5 h-5" />
          </span>
          Soul<span className="text-primary">Up</span>
        </Link>

        <nav className="hidden lg:flex items-center gap-6" aria-label="Menu principal">
          <ul className="flex items-center gap-6">
            {NAV_ITEMS.filter((item) => !item.isCta).map((item) => (
              <li key={item.to}>
                <NavLink
                  to={item.to}
                  end={item.to === '/'}
                  className={({ isActive }) =>
                    `text-sm font-medium transition-colors ${isActive ? 'text-primary' : 'text-text hover:text-primary'}`
                  }
                >
                  {item.label}
                </NavLink>
              </li>
            ))}
          </ul>
          <Link
            to="/contato"
            className="rounded-full bg-primary text-white text-sm font-semibold px-5 py-2.5 hover:bg-primary-dark transition-colors"
          >
            Falar conosco
          </Link>
        </nav>

        <button
          type="button"
          className="lg:hidden flex flex-col justify-center gap-1.5 w-10 h-10"
          aria-label={isMenuOpen ? 'Fechar menu' : 'Abrir menu'}
          aria-expanded={isMenuOpen}
          aria-controls="mobile-nav"
          onClick={() => setIsMenuOpen((open) => !open)}
        >
          <span className={`block h-0.5 w-6 bg-text-dark transition-transform ${isMenuOpen ? 'translate-y-2 rotate-45' : ''}`} />
          <span className={`block h-0.5 w-6 bg-text-dark transition-opacity ${isMenuOpen ? 'opacity-0' : ''}`} />
          <span className={`block h-0.5 w-6 bg-text-dark transition-transform ${isMenuOpen ? '-translate-y-2 -rotate-45' : ''}`} />
        </button>
      </div>

      {isMenuOpen && (
        <nav id="mobile-nav" className="lg:hidden border-t border-border bg-white" aria-label="Menu mobile">
          <ul className="flex flex-col px-4 py-3">
            {NAV_ITEMS.map((item) => (
              <li key={item.to}>
                <NavLink
                  to={item.to}
                  end={item.to === '/'}
                  className={({ isActive }) =>
                    `block py-3 text-sm font-medium border-b border-border last:border-none ${
                      isActive ? 'text-primary' : 'text-text-dark'
                    } ${item.isCta ? 'text-primary font-semibold' : ''}`
                  }
                >
                  {item.label}
                </NavLink>
              </li>
            ))}
          </ul>
        </nav>
      )}
    </header>
  )
}
