/// <reference types="vite/client" />

interface ImportMetaEnv {
  /** URL base da API Java (ex.: https://soulup-api.onrender.com). Sem barra no final. */
  readonly VITE_API_URL?: string
}

interface ImportMeta {
  readonly env: ImportMetaEnv
}
