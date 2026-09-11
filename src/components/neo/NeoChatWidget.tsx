// commit 14/29 (Guilherme): feat(neo) - cria widget do Neo com estado de abertura/fechamento (UI), pronto para IA futura
import { useState } from 'react'
import { BotIcon, SendIcon } from '../icons/Icons'

/**
 * Widget do Neo (Avatar Inteligente) — apenas a interface visual.
 * A integração real com a IA (Watson Assistant) acontece em uma sprint futura;
 * por enquanto o formulário fica desabilitado e não envia nada.
 */
export default function NeoChatWidget() {
  const [isOpen, setIsOpen] = useState(false)

  return (
    <>
      <button
        type="button"
        onClick={() => setIsOpen((open) => !open)}
        aria-label="Conversar com o Neo"
        className="fixed right-4 bottom-4 md:right-6 md:bottom-6 z-50 w-14 h-14 rounded-full bg-primary text-white shadow-lg flex items-center justify-center hover:bg-primary-dark hover:-translate-y-0.5 transition-all"
      >
        <BotIcon className="w-6 h-6" />
      </button>

      {isOpen && (
        <div className="fixed right-4 bottom-20 md:right-6 md:bottom-24 z-50 w-[min(340px,calc(100vw-2rem))] max-h-[min(460px,calc(100vh-140px))] bg-white rounded-2xl shadow-lg border border-border flex flex-col overflow-hidden">
          <div className="bg-primary text-white px-4 py-3 flex items-center justify-between">
            <div>
              <h4 className="font-semibold text-sm">Neo</h4>
              <span className="text-xs text-secondary">Avatar Inteligente · SoulUp</span>
            </div>
            <button
              type="button"
              onClick={() => setIsOpen(false)}
              aria-label="Fechar chat"
              className="text-white/80 hover:text-white text-lg leading-none px-1"
            >
              &times;
            </button>
          </div>

          <div className="flex-1 overflow-y-auto p-4 bg-bg space-y-2">
            <div className="max-w-[85%] rounded-2xl rounded-bl-sm bg-bg-alt text-text-dark text-sm px-3 py-2 leading-snug">
              Oi! Eu sou o Neo 👋 Meu chat ainda está em preparação — na próxima sprint eu vou poder conversar de
              verdade com você por aqui.
            </div>
          </div>

          <form
            className="flex gap-2 p-3 border-t border-border"
            onSubmit={(event) => event.preventDefault()}
            aria-disabled="true"
          >
            <input
              type="text"
              disabled
              placeholder="Em breve disponível..."
              aria-label="Mensagem para o Neo (indisponível nesta sprint)"
              className="flex-1 rounded-full border border-border px-3 py-2 text-sm bg-bg-alt text-text-muted disabled:cursor-not-allowed"
            />
            <button
              type="submit"
              disabled
              aria-label="Enviar mensagem (indisponível nesta sprint)"
              className="w-10 h-10 flex-shrink-0 rounded-full bg-bg-alt text-text-muted flex items-center justify-center disabled:cursor-not-allowed"
            >
              <SendIcon />
            </button>
          </form>
        </div>
      )}
    </>
  )
}
