-- Copia da carga inicial do modelo.sql para os testes automatizados (H2 em modo Oracle).


-- ---------------------------------------------------------------------
-- TB_USUARIOS
-- ---------------------------------------------------------------------
INSERT INTO TB_USUARIOS (usuario_id, nome_usuario, email_usuario, data_cadastro, situacao_usuario)
VALUES (1, 'Ana Beatriz Lima', 'ana.lima@example.com', TO_DATE('2026-01-12', 'YYYY-MM-DD'), 'ATIVO');
INSERT INTO TB_USUARIOS (usuario_id, nome_usuario, email_usuario, data_cadastro, situacao_usuario)
VALUES (2, 'Bruno Carvalho Souza', 'bruno.souza@example.com', TO_DATE('2026-01-20', 'YYYY-MM-DD'), 'ATIVO');
INSERT INTO TB_USUARIOS (usuario_id, nome_usuario, email_usuario, data_cadastro, situacao_usuario)
VALUES (3, 'Camila Rocha Mendes', 'camila.mendes@example.com', TO_DATE('2026-02-03', 'YYYY-MM-DD'), 'ATIVO');
INSERT INTO TB_USUARIOS (usuario_id, nome_usuario, email_usuario, data_cadastro, situacao_usuario)
VALUES (4, 'Diego Fernandes Alves', 'diego.alves@example.com', TO_DATE('2026-02-18', 'YYYY-MM-DD'), 'ATIVO');
INSERT INTO TB_USUARIOS (usuario_id, nome_usuario, email_usuario, data_cadastro, situacao_usuario)
VALUES (5, 'Eduarda Nogueira Prado', 'eduarda.prado@example.com', TO_DATE('2026-03-07', 'YYYY-MM-DD'), 'ATIVO');
INSERT INTO TB_USUARIOS (usuario_id, nome_usuario, email_usuario, data_cadastro, situacao_usuario)
VALUES (6, 'Felipe Martins Costa', 'felipe.costa@example.com', TO_DATE('2026-03-22', 'YYYY-MM-DD'), 'INATIVO');
INSERT INTO TB_USUARIOS (usuario_id, nome_usuario, email_usuario, data_cadastro, situacao_usuario)
VALUES (7, 'Gabriela Teixeira Ramos', 'gabriela.ramos@example.com', TO_DATE('2026-04-10', 'YYYY-MM-DD'), 'ATIVO');
INSERT INTO TB_USUARIOS (usuario_id, nome_usuario, email_usuario, data_cadastro, situacao_usuario)
VALUES (8, 'Henrique Duarte Pires', 'henrique.pires@example.com', TO_DATE('2026-05-05', 'YYYY-MM-DD'), 'BLOQUEADO');
INSERT INTO TB_USUARIOS (usuario_id, nome_usuario, email_usuario, data_cadastro, situacao_usuario)
VALUES (9, 'Isabela Moura Freitas', 'isabela.freitas@example.com', TO_DATE('2026-06-14', 'YYYY-MM-DD'), 'ATIVO');
INSERT INTO TB_USUARIOS (usuario_id, nome_usuario, email_usuario, data_cadastro, situacao_usuario)
VALUES (10, 'João Pedro Barros', 'joao.barros@example.com', TO_DATE('2026-07-01', 'YYYY-MM-DD'), 'INATIVO');

-- ---------------------------------------------------------------------
-- TB_AVATARES
-- ---------------------------------------------------------------------
INSERT INTO TB_AVATARES (avatar_id, nome_avatar, descricao_avatar, personalidade_avatar, situacao_avatar)
VALUES (1, 'Gaia', 'Guardiã da natureza que orienta o usuário na criação de hábitos sustentáveis no dia a dia.', 'Acolhedora e paciente', 'ATIVO');
INSERT INTO TB_AVATARES (avatar_id, nome_avatar, descricao_avatar, personalidade_avatar, situacao_avatar)
VALUES (2, 'Volt', 'Especialista em eficiência energética e consumo consciente de energia elétrica.', 'Motivador e enérgico', 'ATIVO');
INSERT INTO TB_AVATARES (avatar_id, nome_avatar, descricao_avatar, personalidade_avatar, situacao_avatar)
VALUES (3, 'Aqua', 'Assistente focada em economia de água e preservação de recursos hídricos.', 'Calma e didática', 'ATIVO');
INSERT INTO TB_AVATARES (avatar_id, nome_avatar, descricao_avatar, personalidade_avatar, situacao_avatar)
VALUES (4, 'Ciclo', 'Avatar que incentiva reciclagem, reuso de materiais e descarte correto de resíduos.', 'Divertido e direto', 'ATIVO');
INSERT INTO TB_AVATARES (avatar_id, nome_avatar, descricao_avatar, personalidade_avatar, situacao_avatar)
VALUES (5, 'Terra', 'Avatar da versão beta focado em mobilidade sustentável, descontinuado em março de 2026.', 'Formal e objetivo', 'INATIVO');

-- ---------------------------------------------------------------------
-- TB_MISSOES
-- ---------------------------------------------------------------------
INSERT INTO TB_MISSOES (missao_id, titulo_missao, descricao_missao, pontuacao_missao, situacao_missao)
VALUES (1, 'Separação Inteligente', 'Separar corretamente os resíduos recicláveis da casa durante uma semana.', 40, 'ATIVA');
INSERT INTO TB_MISSOES (missao_id, titulo_missao, descricao_missao, pontuacao_missao, situacao_missao)
VALUES (2, 'Desliga da Tomada', 'Retirar da tomada os aparelhos em stand-by durante a noite por 15 dias.', 30, 'ATIVA');
INSERT INTO TB_MISSOES (missao_id, titulo_missao, descricao_missao, pontuacao_missao, situacao_missao)
VALUES (3, 'Banho Consciente', 'Reduzir o tempo de banho para no máximo 5 minutos durante 20 dias.', 50, 'ATIVA');
INSERT INTO TB_MISSOES (missao_id, titulo_missao, descricao_missao, pontuacao_missao, situacao_missao)
VALUES (4, 'Pedal Verde', 'Substituir o carro pela bicicleta em pelo menos 10 deslocamentos.', 80, 'ATIVA');
INSERT INTO TB_MISSOES (missao_id, titulo_missao, descricao_missao, pontuacao_missao, situacao_missao)
VALUES (5, 'Desperdício Zero', 'Aproveitar cascas, talos e sobras de alimentos em pelo menos 3 receitas.', 35, 'ATIVA');
INSERT INTO TB_MISSOES (missao_id, titulo_missao, descricao_missao, pontuacao_missao, situacao_missao)
VALUES (6, 'Iluminação Eficiente', 'Trocar as lâmpadas da residência por modelos LED.', 60, 'ATIVA');
INSERT INTO TB_MISSOES (missao_id, titulo_missao, descricao_missao, pontuacao_missao, situacao_missao)
VALUES (7, 'Água de Reuso', 'Reaproveitar a água da máquina de lavar na limpeza de áreas externas.', 45, 'ATIVA');
INSERT INTO TB_MISSOES (missao_id, titulo_missao, descricao_missao, pontuacao_missao, situacao_missao)
VALUES (8, 'Composteira Caseira', 'Montar e manter uma composteira doméstica por 30 dias.', 90, 'ATIVA');
INSERT INTO TB_MISSOES (missao_id, titulo_missao, descricao_missao, pontuacao_missao, situacao_missao)
VALUES (9, 'Sacola Retornável', 'Usar apenas sacolas retornáveis nas compras durante um mês.', 25, 'ATIVA');
INSERT INTO TB_MISSOES (missao_id, titulo_missao, descricao_missao, pontuacao_missao, situacao_missao)
VALUES (10, 'Carnaval Sustentável', 'Participar da coleta seletiva de resíduos durante o Carnaval de 2026.', 70, 'ENCERRADA');
INSERT INTO TB_MISSOES (missao_id, titulo_missao, descricao_missao, pontuacao_missao, situacao_missao)
VALUES (11, 'Plante uma Árvore', 'Missão piloto de plantio de mudas, suspensa para revisão das parcerias.', 100, 'INATIVA');

-- ---------------------------------------------------------------------
-- TB_USUARIOS_AVATARES (associativa N:N usuario x avatar)
-- ---------------------------------------------------------------------
INSERT INTO TB_USUARIOS_AVATARES (usuario_avatar_id, usuario_id, avatar_id, data_vinculo, status_vinculo)
VALUES (1, 1, 1, TO_DATE('2026-01-12', 'YYYY-MM-DD'), 'ATIVO');
INSERT INTO TB_USUARIOS_AVATARES (usuario_avatar_id, usuario_id, avatar_id, data_vinculo, status_vinculo)
VALUES (2, 1, 4, TO_DATE('2026-03-02', 'YYYY-MM-DD'), 'ATIVO');
INSERT INTO TB_USUARIOS_AVATARES (usuario_avatar_id, usuario_id, avatar_id, data_vinculo, status_vinculo)
VALUES (3, 2, 2, TO_DATE('2026-01-21', 'YYYY-MM-DD'), 'ATIVO');
INSERT INTO TB_USUARIOS_AVATARES (usuario_avatar_id, usuario_id, avatar_id, data_vinculo, status_vinculo)
VALUES (4, 3, 3, TO_DATE('2026-02-03', 'YYYY-MM-DD'), 'ATIVO');
INSERT INTO TB_USUARIOS_AVATARES (usuario_avatar_id, usuario_id, avatar_id, data_vinculo, status_vinculo)
VALUES (5, 4, 5, TO_DATE('2026-02-18', 'YYYY-MM-DD'), 'ENCERRADO');
INSERT INTO TB_USUARIOS_AVATARES (usuario_avatar_id, usuario_id, avatar_id, data_vinculo, status_vinculo)
VALUES (6, 4, 2, TO_DATE('2026-04-01', 'YYYY-MM-DD'), 'ATIVO');
INSERT INTO TB_USUARIOS_AVATARES (usuario_avatar_id, usuario_id, avatar_id, data_vinculo, status_vinculo)
VALUES (7, 5, 1, TO_DATE('2026-03-08', 'YYYY-MM-DD'), 'ATIVO');
INSERT INTO TB_USUARIOS_AVATARES (usuario_avatar_id, usuario_id, avatar_id, data_vinculo, status_vinculo)
VALUES (8, 6, 4, TO_DATE('2026-03-22', 'YYYY-MM-DD'), 'INATIVO');
INSERT INTO TB_USUARIOS_AVATARES (usuario_avatar_id, usuario_id, avatar_id, data_vinculo, status_vinculo)
VALUES (9, 7, 3, TO_DATE('2026-04-10', 'YYYY-MM-DD'), 'ATIVO');
INSERT INTO TB_USUARIOS_AVATARES (usuario_avatar_id, usuario_id, avatar_id, data_vinculo, status_vinculo)
VALUES (10, 8, 2, TO_DATE('2026-05-06', 'YYYY-MM-DD'), 'INATIVO');
INSERT INTO TB_USUARIOS_AVATARES (usuario_avatar_id, usuario_id, avatar_id, data_vinculo, status_vinculo)
VALUES (11, 9, 1, TO_DATE('2026-06-14', 'YYYY-MM-DD'), 'ATIVO');
INSERT INTO TB_USUARIOS_AVATARES (usuario_avatar_id, usuario_id, avatar_id, data_vinculo, status_vinculo)
VALUES (12, 10, 3, TO_DATE('2026-07-02', 'YYYY-MM-DD'), 'INATIVO');
INSERT INTO TB_USUARIOS_AVATARES (usuario_avatar_id, usuario_id, avatar_id, data_vinculo, status_vinculo)
VALUES (13, 7, 4, TO_DATE('2026-06-20', 'YYYY-MM-DD'), 'ATIVO');
INSERT INTO TB_USUARIOS_AVATARES (usuario_avatar_id, usuario_id, avatar_id, data_vinculo, status_vinculo)
VALUES (14, 3, 1, TO_DATE('2026-05-15', 'YYYY-MM-DD'), 'ATIVO');

-- ---------------------------------------------------------------------
-- TB_INTERACOES
-- ---------------------------------------------------------------------
INSERT INTO TB_INTERACOES (interacao_id, usuario_avatar_id, data_interacao, mensagem_usuario, resposta_avatar, tipo_interacao)
VALUES (1, 1, TO_DATE('2026-08-03 08:15', 'YYYY-MM-DD HH24:MI'), 'Bom dia, Gaia! O que posso fazer hoje para reduzir meu lixo?', 'Bom dia, Ana! Que tal começar separando os recicláveis da cozinha? Isso vale a missão Separação Inteligente.', 'SUGESTAO');
INSERT INTO TB_INTERACOES (interacao_id, usuario_avatar_id, data_interacao, mensagem_usuario, resposta_avatar, tipo_interacao)
VALUES (2, 1, TO_DATE('2026-08-10 19:40', 'YYYY-MM-DD HH24:MI'), 'Terminei de separar os recicláveis da semana.', 'Parabéns, Ana! Registrei sua missão como concluída e você ganhou 40 pontos.', 'MISSAO');
INSERT INTO TB_INTERACOES (interacao_id, usuario_avatar_id, data_interacao, mensagem_usuario, resposta_avatar, tipo_interacao)
VALUES (3, 1, TO_DATE('2026-09-14 07:50', 'YYYY-MM-DD HH24:MI'), 'Estou desanimada para pedalar hoje.', 'Entendo, Ana. Lembra que você gosta de pedalar pela manhã? Um trajeto curto já conta para a missão Pedal Verde.', 'CONVERSA');
INSERT INTO TB_INTERACOES (interacao_id, usuario_avatar_id, data_interacao, mensagem_usuario, resposta_avatar, tipo_interacao)
VALUES (4, 2, TO_DATE('2026-09-02 12:30', 'YYYY-MM-DD HH24:MI'), 'Onde descarto pilhas usadas?', 'Pilhas devem ir para pontos de coleta específicos. Há um no mercado perto da sua casa.', 'SUPORTE');
INSERT INTO TB_INTERACOES (interacao_id, usuario_avatar_id, data_interacao, mensagem_usuario, resposta_avatar, tipo_interacao)
VALUES (5, 3, TO_DATE('2026-08-05 21:10', 'YYYY-MM-DD HH24:MI'), 'Minha conta de luz veio alta este mês.', 'Vamos investigar, Bruno! Aparelhos em stand-by pesam na conta. Topa a missão Desliga da Tomada?', 'SUGESTAO');
INSERT INTO TB_INTERACOES (interacao_id, usuario_avatar_id, data_interacao, mensagem_usuario, resposta_avatar, tipo_interacao)
VALUES (6, 3, TO_DATE('2026-08-20 22:00', 'YYYY-MM-DD HH24:MI'), 'Já tirei os aparelhos da tomada todas as noites.', 'Excelente! Seu progresso na missão Desliga da Tomada chegou a 100%.', 'MISSAO');
INSERT INTO TB_INTERACOES (interacao_id, usuario_avatar_id, data_interacao, mensagem_usuario, resposta_avatar, tipo_interacao)
VALUES (7, 3, TO_DATE('2026-10-01 20:45', 'YYYY-MM-DD HH24:MI'), 'Como funciona a pontuação das missões?', 'Cada missão concluída soma pontos ao seu perfil. Missões mais desafiadoras valem mais pontos.', 'SUPORTE');
INSERT INTO TB_INTERACOES (interacao_id, usuario_avatar_id, data_interacao, mensagem_usuario, resposta_avatar, tipo_interacao)
VALUES (8, 4, TO_DATE('2026-08-07 07:30', 'YYYY-MM-DD HH24:MI'), 'Quero economizar água no banho.', 'Ótima ideia, Camila! Banhos de até 5 minutos economizam cerca de 80 litros por dia.', 'SUGESTAO');
INSERT INTO TB_INTERACOES (interacao_id, usuario_avatar_id, data_interacao, mensagem_usuario, resposta_avatar, tipo_interacao)
VALUES (9, 4, TO_DATE('2026-08-28 18:20', 'YYYY-MM-DD HH24:MI'), 'Consegui reduzir meu banho para 5 minutos!', 'Incrível! Missão Banho Consciente concluída. Você economizou cerca de 1.600 litros de água.', 'MISSAO');
INSERT INTO TB_INTERACOES (interacao_id, usuario_avatar_id, data_interacao, mensagem_usuario, resposta_avatar, tipo_interacao)
VALUES (10, 14, TO_DATE('2026-09-18 10:05', 'YYYY-MM-DD HH24:MI'), 'Gaia, me ajuda a montar uma horta em casa?', 'Claro! Comece com temperos em vasos perto da janela. Uma composteira vai gerar adubo para ela.', 'CONVERSA');
INSERT INTO TB_INTERACOES (interacao_id, usuario_avatar_id, data_interacao, mensagem_usuario, resposta_avatar, tipo_interacao)
VALUES (11, 5, TO_DATE('2026-03-10 09:00', 'YYYY-MM-DD HH24:MI'), 'Qual a melhor forma de ir ao trabalho poluindo menos?', 'Transporte público ou bicicleta reduzem bastante a sua emissão de carbono.', 'CONVERSA');
INSERT INTO TB_INTERACOES (interacao_id, usuario_avatar_id, data_interacao, mensagem_usuario, resposta_avatar, tipo_interacao)
VALUES (12, 6, TO_DATE('2026-08-12 13:15', 'YYYY-MM-DD HH24:MI'), 'Vale a pena trocar as lâmpadas por LED?', 'Vale sim, Diego! Lâmpadas LED consomem até 80% menos energia.', 'SUPORTE');
INSERT INTO TB_INTERACOES (interacao_id, usuario_avatar_id, data_interacao, mensagem_usuario, resposta_avatar, tipo_interacao)
VALUES (13, 6, TO_DATE('2026-09-25 19:30', 'YYYY-MM-DD HH24:MI'), 'Troquei metade das lâmpadas de casa.', 'Muito bem! Sua missão Iluminação Eficiente está em 50%.', 'MISSAO');
INSERT INTO TB_INTERACOES (interacao_id, usuario_avatar_id, data_interacao, mensagem_usuario, resposta_avatar, tipo_interacao)
VALUES (14, 7, TO_DATE('2026-08-15 08:40', 'YYYY-MM-DD HH24:MI'), 'Oi Gaia, tudo bem?', 'Tudo ótimo, Eduarda! Vi que você gosta de cozinhar. Que tal aproveitar cascas e talos nas receitas?', 'CONVERSA');
INSERT INTO TB_INTERACOES (interacao_id, usuario_avatar_id, data_interacao, mensagem_usuario, resposta_avatar, tipo_interacao)
VALUES (15, 7, TO_DATE('2026-09-05 17:10', 'YYYY-MM-DD HH24:MI'), 'Fiz um caldo com talos de legumes!', 'Que delícia! Missão Desperdício Zero concluída. Mais 35 pontos para você.', 'MISSAO');
INSERT INTO TB_INTERACOES (interacao_id, usuario_avatar_id, data_interacao, mensagem_usuario, resposta_avatar, tipo_interacao)
VALUES (16, 7, TO_DATE('2026-10-12 18:00', 'YYYY-MM-DD HH24:MI'), 'Qual vai ser meu próximo desafio?', 'Que tal levar sua própria sacola às compras? Combina com a sua ida à feira aos sábados.', 'SUGESTAO');
INSERT INTO TB_INTERACOES (interacao_id, usuario_avatar_id, data_interacao, mensagem_usuario, resposta_avatar, tipo_interacao)
VALUES (17, 8, TO_DATE('2026-04-02 15:00', 'YYYY-MM-DD HH24:MI'), 'Como separo o lixo reciclável?', 'Separe em papel, plástico, metal e vidro, sempre limpos e secos.', 'SUPORTE');
INSERT INTO TB_INTERACOES (interacao_id, usuario_avatar_id, data_interacao, mensagem_usuario, resposta_avatar, tipo_interacao)
VALUES (18, 9, TO_DATE('2026-08-18 06:50', 'YYYY-MM-DD HH24:MI'), 'Posso reaproveitar a água da máquina de lavar?', 'Pode sim, Gabriela! Ela serve para lavar o quintal e a calçada.', 'SUPORTE');
INSERT INTO TB_INTERACOES (interacao_id, usuario_avatar_id, data_interacao, mensagem_usuario, resposta_avatar, tipo_interacao)
VALUES (19, 9, TO_DATE('2026-09-21 07:15', 'YYYY-MM-DD HH24:MI'), 'Comecei a reutilizar a água da máquina.', 'Ótimo! Missão Água de Reuso em 70%. Continue assim!', 'MISSAO');
INSERT INTO TB_INTERACOES (interacao_id, usuario_avatar_id, data_interacao, mensagem_usuario, resposta_avatar, tipo_interacao)
VALUES (20, 13, TO_DATE('2026-10-03 11:30', 'YYYY-MM-DD HH24:MI'), 'Ciclo, quero começar a fazer compostagem.', 'Boa! Uma composteira doméstica reduz até 50% do lixo orgânico. Vou te enviar o passo a passo.', 'SUGESTAO');
INSERT INTO TB_INTERACOES (interacao_id, usuario_avatar_id, data_interacao, mensagem_usuario, resposta_avatar, tipo_interacao)
VALUES (21, 10, TO_DATE('2026-05-20 23:10', 'YYYY-MM-DD HH24:MI'), 'Quero mudar meu e-mail de cadastro.', 'Para sua segurança, a alteração de e-mail precisa ser confirmada pelo link enviado.', 'SUPORTE');
INSERT INTO TB_INTERACOES (interacao_id, usuario_avatar_id, data_interacao, mensagem_usuario, resposta_avatar, tipo_interacao)
VALUES (22, 11, TO_DATE('2026-08-22 20:00', 'YYYY-MM-DD HH24:MI'), 'Ainda não entendi bem como funciona a plataforma.', 'Sem problemas, Isabela! Você completa missões sustentáveis, ganha pontos e eu acompanho a sua evolução.', 'SUPORTE');
INSERT INTO TB_INTERACOES (interacao_id, usuario_avatar_id, data_interacao, mensagem_usuario, resposta_avatar, tipo_interacao)
VALUES (23, 11, TO_DATE('2026-09-30 21:15', 'YYYY-MM-DD HH24:MI'), 'Quais missões combinam comigo?', 'Como você quer deixar o carro em casa, a missão Pedal Verde é perfeita para você!', 'SUGESTAO');
INSERT INTO TB_INTERACOES (interacao_id, usuario_avatar_id, data_interacao, mensagem_usuario, resposta_avatar, tipo_interacao)
VALUES (24, 11, TO_DATE('2026-10-20 19:45', 'YYYY-MM-DD HH24:MI'), 'Completei a primeira semana pedalando!', 'Sensacional, Isabela! Você já está com 60% da missão Pedal Verde.', 'MISSAO');
INSERT INTO TB_INTERACOES (interacao_id, usuario_avatar_id, data_interacao, mensagem_usuario, resposta_avatar, tipo_interacao)
VALUES (25, 12, TO_DATE('2026-07-05 09:30', 'YYYY-MM-DD HH24:MI'), 'Quanto de água gasto lavando louça?', 'Com a torneira aberta, cerca de 117 litros em 15 minutos. Ensaboar com ela fechada reduz bastante.', 'SUPORTE');

-- ---------------------------------------------------------------------
-- TB_USUARIOS_MISSOES (associativa N:N usuario x missao, com progresso)
-- ---------------------------------------------------------------------
INSERT INTO TB_USUARIOS_MISSOES (usuario_missao_id, usuario_id, missao_id, data_inicio, data_conclusao, status_missao, progresso_percentual)
VALUES (1, 1, 1, TO_DATE('2026-08-03', 'YYYY-MM-DD'), TO_DATE('2026-08-10', 'YYYY-MM-DD'), 'CONCLUIDA', 100);
INSERT INTO TB_USUARIOS_MISSOES (usuario_missao_id, usuario_id, missao_id, data_inicio, data_conclusao, status_missao, progresso_percentual)
VALUES (2, 1, 4, TO_DATE('2026-09-01', 'YYYY-MM-DD'), NULL, 'EM_ANDAMENTO', 45);
INSERT INTO TB_USUARIOS_MISSOES (usuario_missao_id, usuario_id, missao_id, data_inicio, data_conclusao, status_missao, progresso_percentual)
VALUES (3, 1, 10, TO_DATE('2026-02-14', 'YYYY-MM-DD'), TO_DATE('2026-02-18', 'YYYY-MM-DD'), 'CONCLUIDA', 100);
INSERT INTO TB_USUARIOS_MISSOES (usuario_missao_id, usuario_id, missao_id, data_inicio, data_conclusao, status_missao, progresso_percentual)
VALUES (4, 1, 9, TO_DATE('2026-10-05', 'YYYY-MM-DD'), NULL, 'PENDENTE', 0);
INSERT INTO TB_USUARIOS_MISSOES (usuario_missao_id, usuario_id, missao_id, data_inicio, data_conclusao, status_missao, progresso_percentual)
VALUES (5, 2, 2, TO_DATE('2026-08-05', 'YYYY-MM-DD'), TO_DATE('2026-08-20', 'YYYY-MM-DD'), 'CONCLUIDA', 100);
INSERT INTO TB_USUARIOS_MISSOES (usuario_missao_id, usuario_id, missao_id, data_inicio, data_conclusao, status_missao, progresso_percentual)
VALUES (6, 2, 6, TO_DATE('2026-09-10', 'YYYY-MM-DD'), NULL, 'EM_ANDAMENTO', 30);
INSERT INTO TB_USUARIOS_MISSOES (usuario_missao_id, usuario_id, missao_id, data_inicio, data_conclusao, status_missao, progresso_percentual)
VALUES (7, 2, 10, TO_DATE('2026-02-14', 'YYYY-MM-DD'), TO_DATE('2026-02-17', 'YYYY-MM-DD'), 'CONCLUIDA', 100);
INSERT INTO TB_USUARIOS_MISSOES (usuario_missao_id, usuario_id, missao_id, data_inicio, data_conclusao, status_missao, progresso_percentual)
VALUES (8, 3, 3, TO_DATE('2026-08-07', 'YYYY-MM-DD'), TO_DATE('2026-08-28', 'YYYY-MM-DD'), 'CONCLUIDA', 100);
INSERT INTO TB_USUARIOS_MISSOES (usuario_missao_id, usuario_id, missao_id, data_inicio, data_conclusao, status_missao, progresso_percentual)
VALUES (9, 3, 1, TO_DATE('2026-06-02', 'YYYY-MM-DD'), TO_DATE('2026-06-09', 'YYYY-MM-DD'), 'CONCLUIDA', 100);
INSERT INTO TB_USUARIOS_MISSOES (usuario_missao_id, usuario_id, missao_id, data_inicio, data_conclusao, status_missao, progresso_percentual)
VALUES (10, 3, 8, TO_DATE('2026-09-20', 'YYYY-MM-DD'), NULL, 'EM_ANDAMENTO', 20);
INSERT INTO TB_USUARIOS_MISSOES (usuario_missao_id, usuario_id, missao_id, data_inicio, data_conclusao, status_missao, progresso_percentual)
VALUES (11, 3, 7, TO_DATE('2026-07-10', 'YYYY-MM-DD'), TO_DATE('2026-07-30', 'YYYY-MM-DD'), 'CONCLUIDA', 100);
INSERT INTO TB_USUARIOS_MISSOES (usuario_missao_id, usuario_id, missao_id, data_inicio, data_conclusao, status_missao, progresso_percentual)
VALUES (12, 4, 6, TO_DATE('2026-08-12', 'YYYY-MM-DD'), NULL, 'EM_ANDAMENTO', 50);
INSERT INTO TB_USUARIOS_MISSOES (usuario_missao_id, usuario_id, missao_id, data_inicio, data_conclusao, status_missao, progresso_percentual)
VALUES (13, 4, 4, TO_DATE('2026-03-11', 'YYYY-MM-DD'), NULL, 'CANCELADA', 20);
INSERT INTO TB_USUARIOS_MISSOES (usuario_missao_id, usuario_id, missao_id, data_inicio, data_conclusao, status_missao, progresso_percentual)
VALUES (14, 5, 5, TO_DATE('2026-08-15', 'YYYY-MM-DD'), TO_DATE('2026-09-05', 'YYYY-MM-DD'), 'CONCLUIDA', 100);
INSERT INTO TB_USUARIOS_MISSOES (usuario_missao_id, usuario_id, missao_id, data_inicio, data_conclusao, status_missao, progresso_percentual)
VALUES (15, 5, 9, TO_DATE('2026-10-12', 'YYYY-MM-DD'), NULL, 'PENDENTE', 0);
INSERT INTO TB_USUARIOS_MISSOES (usuario_missao_id, usuario_id, missao_id, data_inicio, data_conclusao, status_missao, progresso_percentual)
VALUES (16, 5, 3, TO_DATE('2026-04-01', 'YYYY-MM-DD'), TO_DATE('2026-04-21', 'YYYY-MM-DD'), 'CONCLUIDA', 100);
INSERT INTO TB_USUARIOS_MISSOES (usuario_missao_id, usuario_id, missao_id, data_inicio, data_conclusao, status_missao, progresso_percentual)
VALUES (17, 6, 1, TO_DATE('2026-04-02', 'YYYY-MM-DD'), NULL, 'CANCELADA', 30);
INSERT INTO TB_USUARIOS_MISSOES (usuario_missao_id, usuario_id, missao_id, data_inicio, data_conclusao, status_missao, progresso_percentual)
VALUES (18, 7, 7, TO_DATE('2026-08-18', 'YYYY-MM-DD'), NULL, 'EM_ANDAMENTO', 70);
INSERT INTO TB_USUARIOS_MISSOES (usuario_missao_id, usuario_id, missao_id, data_inicio, data_conclusao, status_missao, progresso_percentual)
VALUES (19, 7, 8, TO_DATE('2026-10-03', 'YYYY-MM-DD'), NULL, 'PENDENTE', 0);
INSERT INTO TB_USUARIOS_MISSOES (usuario_missao_id, usuario_id, missao_id, data_inicio, data_conclusao, status_missao, progresso_percentual)
VALUES (20, 7, 2, TO_DATE('2026-05-02', 'YYYY-MM-DD'), TO_DATE('2026-05-17', 'YYYY-MM-DD'), 'CONCLUIDA', 100);
INSERT INTO TB_USUARIOS_MISSOES (usuario_missao_id, usuario_id, missao_id, data_inicio, data_conclusao, status_missao, progresso_percentual)
VALUES (21, 7, 5, TO_DATE('2026-06-25', 'YYYY-MM-DD'), TO_DATE('2026-07-08', 'YYYY-MM-DD'), 'CONCLUIDA', 100);
INSERT INTO TB_USUARIOS_MISSOES (usuario_missao_id, usuario_id, missao_id, data_inicio, data_conclusao, status_missao, progresso_percentual)
VALUES (22, 8, 2, TO_DATE('2026-05-07', 'YYYY-MM-DD'), NULL, 'CANCELADA', 10);
INSERT INTO TB_USUARIOS_MISSOES (usuario_missao_id, usuario_id, missao_id, data_inicio, data_conclusao, status_missao, progresso_percentual)
VALUES (23, 9, 4, TO_DATE('2026-10-01', 'YYYY-MM-DD'), NULL, 'EM_ANDAMENTO', 60);
INSERT INTO TB_USUARIOS_MISSOES (usuario_missao_id, usuario_id, missao_id, data_inicio, data_conclusao, status_missao, progresso_percentual)
VALUES (24, 9, 5, TO_DATE('2026-09-01', 'YYYY-MM-DD'), TO_DATE('2026-09-19', 'YYYY-MM-DD'), 'CONCLUIDA', 100);
INSERT INTO TB_USUARIOS_MISSOES (usuario_missao_id, usuario_id, missao_id, data_inicio, data_conclusao, status_missao, progresso_percentual)
VALUES (25, 10, 3, TO_DATE('2026-07-05', 'YYYY-MM-DD'), TO_DATE('2026-07-25', 'YYYY-MM-DD'), 'CONCLUIDA', 100);

-- ---------------------------------------------------------------------
-- TB_SUGESTOES_AVATAR (somente usuarios ATIVOS - regra de negocio 10)
-- ---------------------------------------------------------------------
INSERT INTO TB_SUGESTOES_AVATAR (sugestao_id, usuario_id, avatar_id, missao_id, data_sugestao, texto_sugestao, status_sugestao)
VALUES (1, 1, 1, 1, TO_DATE('2026-08-03 08:16', 'YYYY-MM-DD HH24:MI'), 'Separe os recicláveis da cozinha esta semana e conquiste 40 pontos.', 'ACEITA');
INSERT INTO TB_SUGESTOES_AVATAR (sugestao_id, usuario_id, avatar_id, missao_id, data_sugestao, texto_sugestao, status_sugestao)
VALUES (2, 1, 1, 4, TO_DATE('2026-08-30 08:05', 'YYYY-MM-DD HH24:MI'), 'Você gosta de pedalar pela manhã: que tal usar a bicicleta também nos deslocamentos da semana?', 'ACEITA');
INSERT INTO TB_SUGESTOES_AVATAR (sugestao_id, usuario_id, avatar_id, missao_id, data_sugestao, texto_sugestao, status_sugestao)
VALUES (3, 1, 4, 8, TO_DATE('2026-09-02 12:35', 'YYYY-MM-DD HH24:MI'), 'Que tal transformar seus resíduos orgânicos em adubo com uma composteira?', 'RECUSADA');
INSERT INTO TB_SUGESTOES_AVATAR (sugestao_id, usuario_id, avatar_id, missao_id, data_sugestao, texto_sugestao, status_sugestao)
VALUES (4, 2, 2, 2, TO_DATE('2026-08-05 21:12', 'YYYY-MM-DD HH24:MI'), 'Desligue os aparelhos em stand-by durante a noite e reduza sua conta de luz.', 'ACEITA');
INSERT INTO TB_SUGESTOES_AVATAR (sugestao_id, usuario_id, avatar_id, missao_id, data_sugestao, texto_sugestao, status_sugestao)
VALUES (5, 2, 2, 6, TO_DATE('2026-09-09 20:30', 'YYYY-MM-DD HH24:MI'), 'Troque suas lâmpadas por LED e economize até 80% de energia na iluminação.', 'ACEITA');
INSERT INTO TB_SUGESTOES_AVATAR (sugestao_id, usuario_id, avatar_id, missao_id, data_sugestao, texto_sugestao, status_sugestao)
VALUES (6, 3, 3, 3, TO_DATE('2026-08-07 07:32', 'YYYY-MM-DD HH24:MI'), 'Reduza o banho para 5 minutos e economize cerca de 80 litros de água por dia.', 'ACEITA');
INSERT INTO TB_SUGESTOES_AVATAR (sugestao_id, usuario_id, avatar_id, missao_id, data_sugestao, texto_sugestao, status_sugestao)
VALUES (7, 3, 1, 8, TO_DATE('2026-09-18 10:07', 'YYYY-MM-DD HH24:MI'), 'Para a sua horta, comece com uma composteira caseira e produza o próprio adubo!', 'ACEITA');
INSERT INTO TB_SUGESTOES_AVATAR (sugestao_id, usuario_id, avatar_id, missao_id, data_sugestao, texto_sugestao, status_sugestao)
VALUES (8, 4, 2, 6, TO_DATE('2026-08-12 13:17', 'YYYY-MM-DD HH24:MI'), 'Lâmpadas LED consomem menos: inicie a missão Iluminação Eficiente.', 'ACEITA');
INSERT INTO TB_SUGESTOES_AVATAR (sugestao_id, usuario_id, avatar_id, missao_id, data_sugestao, texto_sugestao, status_sugestao)
VALUES (9, 4, 2, 3, TO_DATE('2026-10-02 08:10', 'YYYY-MM-DD HH24:MI'), 'Que tal reduzir também o consumo de água com a missão Banho Consciente?', 'ENVIADA');
INSERT INTO TB_SUGESTOES_AVATAR (sugestao_id, usuario_id, avatar_id, missao_id, data_sugestao, texto_sugestao, status_sugestao)
VALUES (10, 5, 1, 5, TO_DATE('2026-08-15 08:42', 'YYYY-MM-DD HH24:MI'), 'Aproveite cascas e talos nas suas receitas e evite o desperdício de alimentos.', 'ACEITA');
INSERT INTO TB_SUGESTOES_AVATAR (sugestao_id, usuario_id, avatar_id, missao_id, data_sugestao, texto_sugestao, status_sugestao)
VALUES (11, 5, 1, 9, TO_DATE('2026-10-12 18:02', 'YYYY-MM-DD HH24:MI'), 'Leve sua sacola retornável para a feira de sábado.', 'ACEITA');
INSERT INTO TB_SUGESTOES_AVATAR (sugestao_id, usuario_id, avatar_id, missao_id, data_sugestao, texto_sugestao, status_sugestao)
VALUES (12, 7, 3, 7, TO_DATE('2026-08-18 06:52', 'YYYY-MM-DD HH24:MI'), 'Reaproveite a água da máquina de lavar para limpar o quintal.', 'ACEITA');
INSERT INTO TB_SUGESTOES_AVATAR (sugestao_id, usuario_id, avatar_id, missao_id, data_sugestao, texto_sugestao, status_sugestao)
VALUES (13, 7, 4, 8, TO_DATE('2026-10-03 11:32', 'YYYY-MM-DD HH24:MI'), 'Monte sua composteira e reduza até 50% do lixo orgânico da casa.', 'ACEITA');
INSERT INTO TB_SUGESTOES_AVATAR (sugestao_id, usuario_id, avatar_id, missao_id, data_sugestao, texto_sugestao, status_sugestao)
VALUES (14, 7, 3, 3, TO_DATE('2026-06-01 07:00', 'YYYY-MM-DD HH24:MI'), 'Que tal reduzir o tempo do seu banho nas próximas semanas?', 'EXPIRADA');
INSERT INTO TB_SUGESTOES_AVATAR (sugestao_id, usuario_id, avatar_id, missao_id, data_sugestao, texto_sugestao, status_sugestao)
VALUES (15, 9, 1, 4, TO_DATE('2026-09-30 21:16', 'YYYY-MM-DD HH24:MI'), 'Você quer deixar o carro em casa: a missão Pedal Verde é perfeita para você!', 'ACEITA');
INSERT INTO TB_SUGESTOES_AVATAR (sugestao_id, usuario_id, avatar_id, missao_id, data_sugestao, texto_sugestao, status_sugestao)
VALUES (16, 9, 1, NULL, TO_DATE('2026-10-25 19:00', 'YYYY-MM-DD HH24:MI'), 'Compartilhe suas conquistas com os amigos e inspire mais pessoas.', 'ENVIADA');
INSERT INTO TB_SUGESTOES_AVATAR (sugestao_id, usuario_id, avatar_id, missao_id, data_sugestao, texto_sugestao, status_sugestao)
VALUES (17, 2, 2, 8, TO_DATE('2026-10-15 21:00', 'YYYY-MM-DD HH24:MI'), 'Que tal começar uma composteira em casa?', 'RECUSADA');
INSERT INTO TB_SUGESTOES_AVATAR (sugestao_id, usuario_id, avatar_id, missao_id, data_sugestao, texto_sugestao, status_sugestao)
VALUES (18, 9, 1, 5, TO_DATE('2026-08-31 20:10', 'YYYY-MM-DD HH24:MI'), 'Experimente reaproveitar alimentos com a missão Desperdício Zero.', 'ACEITA');

-- ---------------------------------------------------------------------
-- TB_LOGS_SEGURANCA (auditoria - endereco_ip nulo em acoes do sistema)
-- ---------------------------------------------------------------------
INSERT INTO TB_LOGS_SEGURANCA (log_id, usuario_id, data_log, tipo_evento, descricao_evento, endereco_ip)
VALUES (1, 8, TO_DATE('2026-05-20 23:05', 'YYYY-MM-DD HH24:MI'), 'LOGIN_SUCESSO', 'Login realizado com sucesso pelo aplicativo móvel.', '203.0.113.77');
INSERT INTO TB_LOGS_SEGURANCA (log_id, usuario_id, data_log, tipo_evento, descricao_evento, endereco_ip)
VALUES (2, 8, TO_DATE('2026-05-20 23:12', 'YYYY-MM-DD HH24:MI'), 'ALTERACAO_EMAIL', 'Solicitação de alteração do e-mail de cadastro.', '203.0.113.77');
INSERT INTO TB_LOGS_SEGURANCA (log_id, usuario_id, data_log, tipo_evento, descricao_evento, endereco_ip)
VALUES (3, 8, TO_DATE('2026-05-21 02:14', 'YYYY-MM-DD HH24:MI'), 'LOGIN_FALHA', 'Senha incorreta informada.', '192.0.2.150');
INSERT INTO TB_LOGS_SEGURANCA (log_id, usuario_id, data_log, tipo_evento, descricao_evento, endereco_ip)
VALUES (4, 8, TO_DATE('2026-05-21 02:15', 'YYYY-MM-DD HH24:MI'), 'LOGIN_FALHA', 'Senha incorreta informada.', '192.0.2.150');
INSERT INTO TB_LOGS_SEGURANCA (log_id, usuario_id, data_log, tipo_evento, descricao_evento, endereco_ip)
VALUES (5, 8, TO_DATE('2026-05-21 02:16', 'YYYY-MM-DD HH24:MI'), 'LOGIN_FALHA', 'Senha incorreta informada.', '192.0.2.150');
INSERT INTO TB_LOGS_SEGURANCA (log_id, usuario_id, data_log, tipo_evento, descricao_evento, endereco_ip)
VALUES (6, 8, TO_DATE('2026-05-21 02:16', 'YYYY-MM-DD HH24:MI'), 'BLOQUEIO_CONTA', 'Conta bloqueada após 3 tentativas de login malsucedidas.', '192.0.2.150');
INSERT INTO TB_LOGS_SEGURANCA (log_id, usuario_id, data_log, tipo_evento, descricao_evento, endereco_ip)
VALUES (7, 6, TO_DATE('2026-06-30 10:00', 'YYYY-MM-DD HH24:MI'), 'DESATIVACAO_CONTA', 'Conta desativada a pedido do usuário.', '198.51.100.90');
INSERT INTO TB_LOGS_SEGURANCA (log_id, usuario_id, data_log, tipo_evento, descricao_evento, endereco_ip)
VALUES (8, 1, TO_DATE('2026-08-03 08:10', 'YYYY-MM-DD HH24:MI'), 'LOGIN_SUCESSO', 'Login realizado com sucesso pelo aplicativo móvel.', '203.0.113.10');
INSERT INTO TB_LOGS_SEGURANCA (log_id, usuario_id, data_log, tipo_evento, descricao_evento, endereco_ip)
VALUES (9, 2, TO_DATE('2026-08-05 21:05', 'YYYY-MM-DD HH24:MI'), 'LOGIN_SUCESSO', 'Login realizado com sucesso pelo navegador.', '198.51.100.24');
INSERT INTO TB_LOGS_SEGURANCA (log_id, usuario_id, data_log, tipo_evento, descricao_evento, endereco_ip)
VALUES (10, 10, TO_DATE('2026-09-01 03:00', 'YYYY-MM-DD HH24:MI'), 'DESATIVACAO_CONTA', 'Conta desativada automaticamente por inatividade superior a 30 dias.', NULL);
INSERT INTO TB_LOGS_SEGURANCA (log_id, usuario_id, data_log, tipo_evento, descricao_evento, endereco_ip)
VALUES (11, 1, TO_DATE('2026-09-14 07:45', 'YYYY-MM-DD HH24:MI'), 'LOGIN_SUCESSO', 'Login realizado com sucesso pelo aplicativo móvel.', '203.0.113.10');
INSERT INTO TB_LOGS_SEGURANCA (log_id, usuario_id, data_log, tipo_evento, descricao_evento, endereco_ip)
VALUES (12, 3, TO_DATE('2026-09-18 09:58', 'YYYY-MM-DD HH24:MI'), 'LOGIN_SUCESSO', 'Login realizado com sucesso pelo navegador.', '2001:db8::3a');
INSERT INTO TB_LOGS_SEGURANCA (log_id, usuario_id, data_log, tipo_evento, descricao_evento, endereco_ip)
VALUES (13, 4, TO_DATE('2026-09-25 19:20', 'YYYY-MM-DD HH24:MI'), 'LOGIN_FALHA', 'Senha incorreta informada.', '198.51.100.61');
INSERT INTO TB_LOGS_SEGURANCA (log_id, usuario_id, data_log, tipo_evento, descricao_evento, endereco_ip)
VALUES (14, 4, TO_DATE('2026-09-25 19:22', 'YYYY-MM-DD HH24:MI'), 'LOGIN_SUCESSO', 'Login realizado com sucesso pelo aplicativo móvel.', '198.51.100.61');
INSERT INTO TB_LOGS_SEGURANCA (log_id, usuario_id, data_log, tipo_evento, descricao_evento, endereco_ip)
VALUES (15, 4, TO_DATE('2026-10-02 07:58', 'YYYY-MM-DD HH24:MI'), 'LOGIN_FALHA', 'Senha incorreta informada a partir de um dispositivo desconhecido.', '203.0.113.200');
INSERT INTO TB_LOGS_SEGURANCA (log_id, usuario_id, data_log, tipo_evento, descricao_evento, endereco_ip)
VALUES (16, 7, TO_DATE('2026-10-03 11:25', 'YYYY-MM-DD HH24:MI'), 'LOGIN_SUCESSO', 'Login realizado com sucesso pelo aplicativo móvel.', '203.0.113.88');
INSERT INTO TB_LOGS_SEGURANCA (log_id, usuario_id, data_log, tipo_evento, descricao_evento, endereco_ip)
VALUES (17, 5, TO_DATE('2026-10-12 17:55', 'YYYY-MM-DD HH24:MI'), 'ALTERACAO_PERFIL', 'Atualização das preferências de notificação.', '203.0.113.45');
INSERT INTO TB_LOGS_SEGURANCA (log_id, usuario_id, data_log, tipo_evento, descricao_evento, endereco_ip)
VALUES (18, 9, TO_DATE('2026-10-20 19:40', 'YYYY-MM-DD HH24:MI'), 'ALTERACAO_SENHA', 'Senha alterada pelo usuário.', '198.51.100.12');
INSERT INTO TB_LOGS_SEGURANCA (log_id, usuario_id, data_log, tipo_evento, descricao_evento, endereco_ip)
VALUES (19, 9, TO_DATE('2026-10-22 08:03', 'YYYY-MM-DD HH24:MI'), 'LOGIN_FALHA', 'Senha incorreta informada.', '198.51.100.12');
INSERT INTO TB_LOGS_SEGURANCA (log_id, usuario_id, data_log, tipo_evento, descricao_evento, endereco_ip)
VALUES (20, 9, TO_DATE('2026-10-22 08:04', 'YYYY-MM-DD HH24:MI'), 'LOGIN_FALHA', 'Senha incorreta informada.', '198.51.100.12');
INSERT INTO TB_LOGS_SEGURANCA (log_id, usuario_id, data_log, tipo_evento, descricao_evento, endereco_ip)
VALUES (21, 9, TO_DATE('2026-10-22 08:05', 'YYYY-MM-DD HH24:MI'), 'LOGIN_SUCESSO', 'Login realizado com sucesso após redefinição recente de senha.', '198.51.100.12');

-- ---------------------------------------------------------------------
-- TB_MEMORIAS_USUARIO (o que o avatar aprendeu sobre cada usuario)
-- ---------------------------------------------------------------------
INSERT INTO TB_MEMORIAS_USUARIO (memoria_id, usuario_avatar_id, categoria_memoria, chave_memoria, valor_memoria, relevancia_memoria, data_registro, data_ultima_atualizacao, status_memoria)
VALUES (1, 1, 'PREFERENCIA', 'atividade_fisica', 'Gosta de pedalar pela manhã nos fins de semana.', 90, TO_DATE('2026-08-10', 'YYYY-MM-DD'), TO_DATE('2026-09-14', 'YYYY-MM-DD'), 'ATIVA');
INSERT INTO TB_MEMORIAS_USUARIO (memoria_id, usuario_avatar_id, categoria_memoria, chave_memoria, valor_memoria, relevancia_memoria, data_registro, data_ultima_atualizacao, status_memoria)
VALUES (2, 1, 'HORARIO_ATIVO', 'horario_pico_uso', 'Interage com mais frequência entre 7h e 8h da manhã.', 75, TO_DATE('2026-08-10', 'YYYY-MM-DD'), TO_DATE('2026-09-14', 'YYYY-MM-DD'), 'ATIVA');
INSERT INTO TB_MEMORIAS_USUARIO (memoria_id, usuario_avatar_id, categoria_memoria, chave_memoria, valor_memoria, relevancia_memoria, data_registro, data_ultima_atualizacao, status_memoria)
VALUES (3, 1, 'HUMOR', 'humor_recente', 'Relatou desânimo para pedalar em 14/09.', 60, TO_DATE('2026-09-14', 'YYYY-MM-DD'), TO_DATE('2026-09-14', 'YYYY-MM-DD'), 'ATIVA');
INSERT INTO TB_MEMORIAS_USUARIO (memoria_id, usuario_avatar_id, categoria_memoria, chave_memoria, valor_memoria, relevancia_memoria, data_registro, data_ultima_atualizacao, status_memoria)
VALUES (4, 2, 'INTERESSE', 'descarte_especial', 'Tem dúvidas sobre o descarte de pilhas e eletrônicos.', 70, TO_DATE('2026-09-02', 'YYYY-MM-DD'), TO_DATE('2026-09-02', 'YYYY-MM-DD'), 'ATIVA');
INSERT INTO TB_MEMORIAS_USUARIO (memoria_id, usuario_avatar_id, categoria_memoria, chave_memoria, valor_memoria, relevancia_memoria, data_registro, data_ultima_atualizacao, status_memoria)
VALUES (5, 3, 'PREFERENCIA', 'foco_economia', 'Prioriza ações que reduzam a conta de luz.', 95, TO_DATE('2026-08-05', 'YYYY-MM-DD'), TO_DATE('2026-08-20', 'YYYY-MM-DD'), 'ATIVA');
INSERT INTO TB_MEMORIAS_USUARIO (memoria_id, usuario_avatar_id, categoria_memoria, chave_memoria, valor_memoria, relevancia_memoria, data_registro, data_ultima_atualizacao, status_memoria)
VALUES (6, 3, 'HORARIO_ATIVO', 'horario_pico_uso', 'Usa a plataforma no período noturno, após as 20h.', 80, TO_DATE('2026-08-05', 'YYYY-MM-DD'), TO_DATE('2026-10-01', 'YYYY-MM-DD'), 'ATIVA');
INSERT INTO TB_MEMORIAS_USUARIO (memoria_id, usuario_avatar_id, categoria_memoria, chave_memoria, valor_memoria, relevancia_memoria, data_registro, data_ultima_atualizacao, status_memoria)
VALUES (7, 4, 'ROTINA', 'tempo_banho', 'Reduziu o tempo de banho para 5 minutos.', 65, TO_DATE('2026-08-07', 'YYYY-MM-DD'), TO_DATE('2026-08-28', 'YYYY-MM-DD'), 'ATIVA');
INSERT INTO TB_MEMORIAS_USUARIO (memoria_id, usuario_avatar_id, categoria_memoria, chave_memoria, valor_memoria, relevancia_memoria, data_registro, data_ultima_atualizacao, status_memoria)
VALUES (8, 14, 'INTERESSE', 'horta_caseira', 'Quer montar uma horta de temperos em casa.', 85, TO_DATE('2026-09-18', 'YYYY-MM-DD'), TO_DATE('2026-09-18', 'YYYY-MM-DD'), 'ATIVA');
INSERT INTO TB_MEMORIAS_USUARIO (memoria_id, usuario_avatar_id, categoria_memoria, chave_memoria, valor_memoria, relevancia_memoria, data_registro, data_ultima_atualizacao, status_memoria)
VALUES (9, 7, 'PREFERENCIA', 'culinaria', 'Gosta de cozinhar e de reaproveitar alimentos.', 90, TO_DATE('2026-08-15', 'YYYY-MM-DD'), TO_DATE('2026-09-05', 'YYYY-MM-DD'), 'ATIVA');
INSERT INTO TB_MEMORIAS_USUARIO (memoria_id, usuario_avatar_id, categoria_memoria, chave_memoria, valor_memoria, relevancia_memoria, data_registro, data_ultima_atualizacao, status_memoria)
VALUES (10, 7, 'ROTINA', 'feira_semanal', 'Faz compras na feira aos sábados pela manhã.', 80, TO_DATE('2026-09-20', 'YYYY-MM-DD'), TO_DATE('2026-10-12', 'YYYY-MM-DD'), 'ATIVA');
INSERT INTO TB_MEMORIAS_USUARIO (memoria_id, usuario_avatar_id, categoria_memoria, chave_memoria, valor_memoria, relevancia_memoria, data_registro, data_ultima_atualizacao, status_memoria)
VALUES (11, 9, 'EVENTO_IMPORTANTE', 'mudanca_casa', 'Mudou-se para uma casa com quintal em agosto.', 70, TO_DATE('2026-08-18', 'YYYY-MM-DD'), TO_DATE('2026-08-18', 'YYYY-MM-DD'), 'ATIVA');
INSERT INTO TB_MEMORIAS_USUARIO (memoria_id, usuario_avatar_id, categoria_memoria, chave_memoria, valor_memoria, relevancia_memoria, data_registro, data_ultima_atualizacao, status_memoria)
VALUES (12, 11, 'ROTINA', 'transporte_trabalho', 'Vai ao trabalho de carro, mas quer trocar pela bicicleta.', 95, TO_DATE('2026-09-30', 'YYYY-MM-DD'), TO_DATE('2026-10-20', 'YYYY-MM-DD'), 'ATIVA');
INSERT INTO TB_MEMORIAS_USUARIO (memoria_id, usuario_avatar_id, categoria_memoria, chave_memoria, valor_memoria, relevancia_memoria, data_registro, data_ultima_atualizacao, status_memoria)
VALUES (13, 11, 'HUMOR', 'motivacao', 'Demonstra empolgação com as primeiras conquistas na plataforma.', 55, TO_DATE('2026-10-20', 'YYYY-MM-DD'), TO_DATE('2026-10-20', 'YYYY-MM-DD'), 'ATIVA');
INSERT INTO TB_MEMORIAS_USUARIO (memoria_id, usuario_avatar_id, categoria_memoria, chave_memoria, valor_memoria, relevancia_memoria, data_registro, data_ultima_atualizacao, status_memoria)
VALUES (14, 5, 'PREFERENCIA', 'meio_transporte', 'Prefere transporte público ao carro.', 50, TO_DATE('2026-03-10', 'YYYY-MM-DD'), TO_DATE('2026-03-10', 'YYYY-MM-DD'), 'ARQUIVADA');
INSERT INTO TB_MEMORIAS_USUARIO (memoria_id, usuario_avatar_id, categoria_memoria, chave_memoria, valor_memoria, relevancia_memoria, data_registro, data_ultima_atualizacao, status_memoria)
VALUES (15, 8, 'INTERESSE', 'reciclagem_basica', 'Buscava aprender a separar o lixo reciclável.', 40, TO_DATE('2026-04-02', 'YYYY-MM-DD'), TO_DATE('2026-04-02', 'YYYY-MM-DD'), 'EXPIRADA');
INSERT INTO TB_MEMORIAS_USUARIO (memoria_id, usuario_avatar_id, categoria_memoria, chave_memoria, valor_memoria, relevancia_memoria, data_registro, data_ultima_atualizacao, status_memoria)
VALUES (16, 6, 'INTERESSE', 'iluminacao_led', 'Está trocando as lâmpadas de casa por modelos LED.', 75, TO_DATE('2026-08-12', 'YYYY-MM-DD'), TO_DATE('2026-09-25', 'YYYY-MM-DD'), 'ATIVA');
INSERT INTO TB_MEMORIAS_USUARIO (memoria_id, usuario_avatar_id, categoria_memoria, chave_memoria, valor_memoria, relevancia_memoria, data_registro, data_ultima_atualizacao, status_memoria)
VALUES (17, 13, 'INTERESSE', 'compostagem', 'Quer começar a fazer compostagem doméstica.', 85, TO_DATE('2026-10-03', 'YYYY-MM-DD'), TO_DATE('2026-10-03', 'YYYY-MM-DD'), 'ATIVA');

