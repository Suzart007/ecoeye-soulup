-- Copia da estrutura do modelo.sql para os testes automatizados (H2 em modo Oracle).
-- A trigger TRG_SUG_USUARIO_ATIVO (PL/SQL) fica de fora: a regra 10 tambem e validada na API.

CREATE TABLE TB_USUARIOS (
    usuario_id INTEGER NOT NULL,
    nome_usuario VARCHAR2(120) NOT NULL,
    email_usuario VARCHAR2(150) NOT NULL,
    data_cadastro DATE NOT NULL,
    situacao_usuario VARCHAR2(20) NOT NULL,
    CONSTRAINT pk_tb_usuarios PRIMARY KEY (usuario_id),
    CONSTRAINT uk_tb_usuarios_email UNIQUE (email_usuario),
    CONSTRAINT ck_tb_usuarios_situacao CHECK (situacao_usuario IN ('ATIVO', 'INATIVO', 'BLOQUEADO'))
);

CREATE TABLE TB_AVATARES (
    avatar_id INTEGER NOT NULL,
    nome_avatar VARCHAR2(100) NOT NULL,
    descricao_avatar VARCHAR2(255) NOT NULL,
    personalidade_avatar VARCHAR2(80) NOT NULL,
    situacao_avatar VARCHAR2(20) NOT NULL,
    CONSTRAINT pk_tb_avatares PRIMARY KEY (avatar_id),
    CONSTRAINT ck_tb_avatares_situacao CHECK (situacao_avatar IN ('ATIVO', 'INATIVO'))
);

CREATE TABLE TB_USUARIOS_AVATARES (
    usuario_avatar_id INTEGER NOT NULL,
    usuario_id INTEGER NOT NULL,
    avatar_id INTEGER NOT NULL,
    data_vinculo DATE NOT NULL,
    status_vinculo VARCHAR2(20) NOT NULL,
    CONSTRAINT pk_tb_usuarios_avatares PRIMARY KEY (usuario_avatar_id),
    CONSTRAINT fk_ua_usuario FOREIGN KEY (usuario_id) REFERENCES TB_USUARIOS(usuario_id),
    CONSTRAINT fk_ua_avatar FOREIGN KEY (avatar_id) REFERENCES TB_AVATARES(avatar_id),
    CONSTRAINT ck_ua_status CHECK (status_vinculo IN ('ATIVO', 'INATIVO', 'ENCERRADO'))
);

CREATE TABLE TB_INTERACOES (
    interacao_id INTEGER NOT NULL,
    usuario_avatar_id INTEGER NOT NULL,
    data_interacao DATE NOT NULL,
    mensagem_usuario VARCHAR2(500) NOT NULL,
    resposta_avatar VARCHAR2(500) NOT NULL,
    tipo_interacao VARCHAR2(50) NOT NULL,
    CONSTRAINT pk_tb_interacoes PRIMARY KEY (interacao_id),
    CONSTRAINT fk_interacoes_ua FOREIGN KEY (usuario_avatar_id) REFERENCES TB_USUARIOS_AVATARES(usuario_avatar_id),
    CONSTRAINT ck_interacoes_tipo CHECK (tipo_interacao IN ('CONVERSA', 'SUGESTAO', 'MISSAO', 'SUPORTE'))
);

CREATE TABLE TB_MISSOES (
    missao_id INTEGER NOT NULL,
    titulo_missao VARCHAR2(120) NOT NULL,
    descricao_missao VARCHAR2(255) NOT NULL,
    pontuacao_missao NUMBER(3) NOT NULL,
    situacao_missao VARCHAR2(20) NOT NULL,
    CONSTRAINT pk_tb_missoes PRIMARY KEY (missao_id),
    CONSTRAINT ck_missoes_pontos CHECK (pontuacao_missao BETWEEN 0 AND 100),
    CONSTRAINT ck_missoes_situacao CHECK (situacao_missao IN ('ATIVA', 'INATIVA', 'ENCERRADA'))
);

CREATE TABLE TB_USUARIOS_MISSOES (
    usuario_missao_id INTEGER NOT NULL,
    usuario_id INTEGER NOT NULL,
    missao_id INTEGER NOT NULL,
    data_inicio DATE NOT NULL,
    data_conclusao DATE,
    status_missao VARCHAR2(20) NOT NULL,
    progresso_percentual NUMBER(3) NOT NULL,
    CONSTRAINT pk_tb_usuarios_missoes PRIMARY KEY (usuario_missao_id),
    CONSTRAINT fk_um_usuario FOREIGN KEY (usuario_id) REFERENCES TB_USUARIOS(usuario_id),
    CONSTRAINT fk_um_missao FOREIGN KEY (missao_id) REFERENCES TB_MISSOES(missao_id),
    CONSTRAINT ck_um_status CHECK (status_missao IN ('PENDENTE', 'EM_ANDAMENTO', 'CONCLUIDA', 'CANCELADA')),
    CONSTRAINT ck_um_progresso CHECK (progresso_percentual BETWEEN 0 AND 100),
    -- [Sprint 4] a conclusao nao pode ser anterior ao inicio da missao
    CONSTRAINT ck_um_datas CHECK (data_conclusao IS NULL OR data_conclusao >= data_inicio),
    -- [Sprint 4] missao concluida exige data de conclusao e 100% de progresso
    CONSTRAINT ck_um_conclusao CHECK (status_missao <> 'CONCLUIDA'
                                      OR (data_conclusao IS NOT NULL AND progresso_percentual = 100))
);

CREATE TABLE TB_SUGESTOES_AVATAR (
    sugestao_id INTEGER NOT NULL,
    usuario_id INTEGER NOT NULL,
    avatar_id INTEGER NOT NULL,
    missao_id INTEGER,
    data_sugestao DATE NOT NULL,
    texto_sugestao VARCHAR2(255) NOT NULL,
    status_sugestao VARCHAR2(20) NOT NULL,
    CONSTRAINT pk_tb_sugestoes_avatar PRIMARY KEY (sugestao_id),
    CONSTRAINT fk_sug_usuario FOREIGN KEY (usuario_id) REFERENCES TB_USUARIOS(usuario_id),
    CONSTRAINT fk_sug_avatar FOREIGN KEY (avatar_id) REFERENCES TB_AVATARES(avatar_id),
    CONSTRAINT fk_sug_missao FOREIGN KEY (missao_id) REFERENCES TB_MISSOES(missao_id),
    CONSTRAINT ck_sug_status CHECK (status_sugestao IN ('ENVIADA', 'ACEITA', 'RECUSADA', 'EXPIRADA'))
);

CREATE TABLE TB_LOGS_SEGURANCA (
    log_id INTEGER NOT NULL,
    usuario_id INTEGER NOT NULL,
    data_log DATE NOT NULL,
    tipo_evento VARCHAR2(80) NOT NULL,
    descricao_evento VARCHAR2(255) NOT NULL,
    endereco_ip VARCHAR2(45),
    CONSTRAINT pk_tb_logs_seguranca PRIMARY KEY (log_id),
    CONSTRAINT fk_logs_usuario FOREIGN KEY (usuario_id) REFERENCES TB_USUARIOS(usuario_id)
);

CREATE TABLE TB_MEMORIAS_USUARIO (
    memoria_id INTEGER NOT NULL,
    usuario_avatar_id INTEGER NOT NULL,
    categoria_memoria VARCHAR2(30) NOT NULL,
    chave_memoria VARCHAR2(100) NOT NULL,
    valor_memoria VARCHAR2(500) NOT NULL,
    relevancia_memoria NUMBER(3) NOT NULL,
    data_registro DATE NOT NULL,
    data_ultima_atualizacao DATE NOT NULL,
    status_memoria VARCHAR2(20) NOT NULL,
    CONSTRAINT pk_tb_memorias_usuario PRIMARY KEY (memoria_id),
    CONSTRAINT fk_memorias_usuario_avatar FOREIGN KEY (usuario_avatar_id) REFERENCES TB_USUARIOS_AVATARES(usuario_avatar_id),
    CONSTRAINT uk_tb_memorias_chave UNIQUE (usuario_avatar_id, chave_memoria),
    CONSTRAINT ck_memorias_categoria CHECK (categoria_memoria IN ('PREFERENCIA', 'ROTINA', 'INTERESSE', 'HUMOR', 'HORARIO_ATIVO', 'EVENTO_IMPORTANTE')),
    CONSTRAINT ck_memorias_relevancia CHECK (relevancia_memoria BETWEEN 0 AND 100),
    CONSTRAINT ck_memorias_status CHECK (status_memoria IN ('ATIVA', 'ARQUIVADA', 'EXPIRADA')),
    -- [Sprint 4] a ultima atualizacao nao pode ser anterior ao registro da memoria
    CONSTRAINT ck_memorias_datas CHECK (data_ultima_atualizacao >= data_registro)
);

