import { useContext } from 'react'
import { ToastContext } from '../context/toast-context'

/** Dá acesso ao `showToast` de qualquer componente abaixo do <ToastProvider>. */
export function useToast() {
  const context = useContext(ToastContext)
  if (!context) {
    throw new Error('useToast deve ser usado dentro de <ToastProvider>.')
  }
  return context
}
