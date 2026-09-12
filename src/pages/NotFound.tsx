// commit 19/29 (Kaio): feat - cria página 404 para rotas inexistentes
import { useDocumentTitle } from '../hooks/useDocumentTitle'
import Button from '../components/common/Button'

export default function NotFound() {
  useDocumentTitle('Página não encontrada')

  return (
    <section className="py-24 text-center px-4">
      <span className="text-6xl font-extrabold text-bg-alt block mb-4">404</span>
      <h1 className="text-2xl font-bold text-text-dark mb-2">Página não encontrada</h1>
      <p className="text-text-muted mb-8">A página que você procura não existe ou foi movida.</p>
      <Button to="/">Voltar para a Home</Button>
    </section>
  )
}
