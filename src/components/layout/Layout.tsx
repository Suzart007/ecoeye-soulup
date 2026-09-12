// commit 16/29 (Kaio): feat(layout) - cria componente Layout com Outlet do React Router
import { Outlet } from 'react-router-dom'
import Header from './Header'
import Footer from './Footer'
import NeoChatWidget from '../neo/NeoChatWidget'

export default function Layout() {
  return (
    <div className="min-h-screen flex flex-col">
      <a className="skip-link" href="#main-content">
        Pular para o conteúdo
      </a>

      <Header />

      <main id="main-content" className="flex-1">
        <Outlet />
      </main>

      <Footer />

      <NeoChatWidget />
    </div>
  )
}
