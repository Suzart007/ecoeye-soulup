// commit 01/29 (Henry): chore(setup) - configura Vite + React + TypeScript + Tailwind CSS
import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'

export default defineConfig({
  plugins: [react(), tailwindcss()],
})
