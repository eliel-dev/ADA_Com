-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 10/07/2023 às 21:10
-- Versão do servidor: 10.4.28-MariaDB
-- Versão do PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `ada_com_db`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `catalogo`
--

CREATE TABLE `catalogo` (
  `Item_ID` int(11) NOT NULL,
  `Tipo` int(11) NOT NULL,
  `nome` varchar(65) NOT NULL COMMENT 'Nome do serviço ou marca e modelo do produto',
  `categoria` varchar(65) NOT NULL,
  `valor` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Despejando dados para a tabela `catalogo`
--

INSERT INTO `catalogo` (`Item_ID`, `Tipo`, `nome`, `categoria`, `valor`) VALUES
(1, 1, 'Tp-Link Archer GX90', 'Roteador', 1499.99),
(2, 1, 'Asus RT-AX82U', 'Roteador', 1199.99),
(3, 1, 'Samsung 75QN900A', 'TV', 43699.05),
(4, 1, 'LG 86NANO75SQA', 'TV', 10999.00),
(5, 1, 'PlayStation 5', 'Console', 7000.90),
(6, 1, 'Xbox Series X', 'Console', 5000.00),
(7, 1, 'Xbox Series S', 'Console', 3000.00),
(8, 2, 'TV 1', 'TV online', 39.90),
(9, 2, 'TV 2', 'TV online', 59.90),
(10, 2, 'TV 3', 'TV online', 79.90),
(11, 2, '100 Mega', 'Internet', 170.90),
(12, 2, '500 Mega', 'Internet', 300.78),
(13, 2, '1 Giga', 'Internet', 500.90),
(14, 2, 'PC', 'Jogos por assinatura', 20.49);

-- --------------------------------------------------------

--
-- Estrutura para tabela `cidade`
--

CREATE TABLE `cidade` (
  `id_Cidade` int(11) NOT NULL,
  `id_Estado` int(11) NOT NULL,
  `Nome_Cidade` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Despejando dados para a tabela `cidade`
--

INSERT INTO `cidade` (`id_Cidade`, `id_Estado`, `Nome_Cidade`) VALUES
(1, 1, 'Florianópolis'),
(2, 1, 'Joinville'),
(3, 1, 'Blumenau'),
(4, 1, 'São José'),
(5, 2, 'São Paulo'),
(6, 2, 'Campinas'),
(7, 2, 'Santos'),
(8, 2, 'Ribeirão Preto');

-- --------------------------------------------------------

--
-- Estrutura para tabela `cliente`
--

CREATE TABLE `cliente` (
  `Cliente_ID` int(11) NOT NULL,
  `Endereco_idEndereco` int(11) NOT NULL,
  `NomeCliente` varchar(60) NOT NULL,
  `SobreNomeCliente` varchar(60) NOT NULL,
  `cnpj_cpf` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Despejando dados para a tabela `cliente`
--

INSERT INTO `cliente` (`Cliente_ID`, `Endereco_idEndereco`, `NomeCliente`, `SobreNomeCliente`, `cnpj_cpf`) VALUES
(4, 3, 'Ana', 'Pereira', 33333333333),
(7, 20, 'Carlas', 'Costa', 77777777748),
(49, 2, 'dsfsd', 'sdfgds', 68989898774),
(1, 16, 'João', 'Silva', 44444444444),
(3, 18, 'José', 'Oliveir8', 22222222229),
(5, 19, 'Julia', 'Ferreira', 55555555555),
(2, 17, 'Maria', 'Santos', 11111111112),
(8, 21, 'Mario8', 'Bueno8', 88888888888);

-- --------------------------------------------------------

--
-- Estrutura para tabela `colaborador`
--

CREATE TABLE `colaborador` (
  `Colaborador_ID` int(11) NOT NULL,
  `NomeColaborador` varchar(45) NOT NULL,
  `SobreNomeColab` varchar(60) NOT NULL,
  `Nivel` int(11) NOT NULL DEFAULT 1,
  `Usuario` text NOT NULL,
  `Senha` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Despejando dados para a tabela `colaborador`
--

INSERT INTO `colaborador` (`Colaborador_ID`, `NomeColaborador`, `SobreNomeColab`, `Nivel`, `Usuario`, `Senha`) VALUES
(1, 'João', 'Silva', 2, 'gestor', '123'),
(2, 'Careca', 'Jao', 1, 'Jao.Careca', '123'),
(3, 'v', 'teste', 1, 'teste.v', '123');

-- --------------------------------------------------------

--
-- Estrutura para tabela `comissao`
--

CREATE TABLE `comissao` (
  `Comissao_ID` int(11) NOT NULL,
  `colaborador_Colaborador_ID` int(11) NOT NULL,
  `TaxaComissao` double NOT NULL,
  `Data` date NOT NULL,
  `Valor_comissao` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Despejando dados para a tabela `comissao`
--

INSERT INTO `comissao` (`Comissao_ID`, `colaborador_Colaborador_ID`, `TaxaComissao`, `Data`, `Valor_comissao`) VALUES
(1, 2, 0.03, '2023-06-26', 659.94),
(2, 2, 0.03, '2023-06-27', 288.00),
(3, 2, 0.03, '2023-06-27', 659.94),
(4, 2, 0.05, '2023-06-27', 13109.72),
(5, 2, 0.05, '2023-06-27', 17479.62),
(6, 3, 0.03, '2023-06-27', 20.15);

-- --------------------------------------------------------

--
-- Estrutura para tabela `endereco`
--

CREATE TABLE `endereco` (
  `idEndereco` int(11) NOT NULL,
  `Cidade_id_Cidade` int(11) NOT NULL,
  `Cidade_id_Estado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Despejando dados para a tabela `endereco`
--

INSERT INTO `endereco` (`idEndereco`, `Cidade_id_Cidade`, `Cidade_id_Estado`) VALUES
(1, 1, 1),
(2, 2, 1),
(3, 3, 1),
(4, 4, 1),
(5, 5, 2),
(6, 6, 2),
(7, 7, 2),
(8, 8, 2),
(16, 1, 1),
(17, 2, 1),
(18, 3, 1),
(19, 6, 2),
(20, 8, 2),
(21, 5, 2);

-- --------------------------------------------------------

--
-- Estrutura para tabela `estado`
--

CREATE TABLE `estado` (
  `id_Estado` int(11) NOT NULL,
  `Nome_estado` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Despejando dados para a tabela `estado`
--

INSERT INTO `estado` (`id_Estado`, `Nome_estado`) VALUES
(1, 'Santa Catarina'),
(2, 'São Paulo');

-- --------------------------------------------------------

--
-- Estrutura para tabela `expalternativas`
--

CREATE TABLE `expalternativas` (
  `expAlternativas_id` int(11) NOT NULL,
  `expperguntas_expPerguntas_id` int(11) NOT NULL,
  `Alternativas` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Despejando dados para a tabela `expalternativas`
--

INSERT INTO `expalternativas` (`expAlternativas_id`, `expperguntas_expPerguntas_id`, `Alternativas`) VALUES
(4, 1, 'Em um evento de Tecnologia e entretenimento.'),
(3, 1, 'Pesquisando online.'),
(1, 1, 'Post no Instagram.'),
(2, 1, 'Recomendação de conhecidos.'),
(8, 2, 'Pesquisar serviços de internet e jogos por assinatura.'),
(17, 2, 'Post no Instagram.'),
(6, 2, 'Verificar área de atuação da empresa.'),
(7, 2, 'Verificar canais do pacote TV e seus adicionais.'),
(5, 2, 'Verificar preço e formas de compra.'),
(10, 3, 'Conhecia, pois pessoas próximas são clientes.'),
(11, 3, 'Sou cliente em um serviço específico.'),
(9, 3, 'Vi apenas em uma publicidade.'),
(15, 4, 'Não.'),
(13, 4, 'Sim, em uma feira tecnologia e entretenimento.'),
(12, 4, 'Sim, Festa de aniversario da cidade.'),
(14, 4, 'Sim, onde a empresa era um dos patrocinadores.');

-- --------------------------------------------------------

--
-- Estrutura para tabela `experiencia_venda`
--

CREATE TABLE `experiencia_venda` (
  `Experiencia_ID` int(11) NOT NULL,
  `Perguntas_id` int(11) NOT NULL,
  `cliente_Cliente_ID` int(11) NOT NULL,
  `Alternativas_id` int(11) NOT NULL,
  `registrovenda_ID` int(11) NOT NULL,
  `Data` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Despejando dados para a tabela `experiencia_venda`
--

INSERT INTO `experiencia_venda` (`Experiencia_ID`, `Perguntas_id`, `cliente_Cliente_ID`, `Alternativas_id`, `registrovenda_ID`, `Data`) VALUES
(2, 1, 2, 1, 2, NULL),
(3, 2, 2, 17, 2, NULL),
(4, 3, 2, 11, 2, NULL),
(5, 4, 2, 15, 2, NULL),
(8, 1, 5, 3, 5, NULL),
(9, 2, 5, 6, 5, NULL),
(10, 3, 5, 9, 5, NULL),
(11, 4, 5, 15, 5, NULL),
(12, 1, 7, 1, 6, NULL),
(13, 2, 7, 7, 6, NULL),
(14, 3, 7, 9, 6, NULL),
(15, 4, 7, 13, 6, NULL),
(16, 1, 7, 1, 7, NULL),
(17, 2, 7, 8, 7, NULL),
(18, 3, 7, 9, 7, NULL),
(19, 4, 7, 12, 7, NULL),
(20, 1, 5, 1, 8, NULL),
(21, 2, 5, 6, 8, NULL),
(22, 3, 5, 11, 8, NULL),
(23, 4, 5, 15, 8, NULL),
(24, 1, 7, 1, 9, NULL),
(25, 2, 7, 7, 9, NULL),
(26, 3, 7, 9, 9, NULL),
(27, 4, 7, 13, 9, NULL);

-- --------------------------------------------------------

--
-- Estrutura para tabela `expperguntas`
--

CREATE TABLE `expperguntas` (
  `expPerguntas_id` int(11) NOT NULL,
  `Pergunta` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Despejando dados para a tabela `expperguntas`
--

INSERT INTO `expperguntas` (`expPerguntas_id`, `Pergunta`) VALUES
(1, 'Onde você ouviu falar da nossa empresa/produto/serviço pela primeira vez?'),
(2, 'Qual foi o motivo que o levou a visitar nosso site?'),
(3, 'Como você descreveria sua familiaridade com nossa empresa antes de visitar nosso site?'),
(4, 'Você participou de algum evento ou feira onde nossa empresa estava presente?');

-- --------------------------------------------------------

--
-- Estrutura para tabela `registrovenda`
--

CREATE TABLE `registrovenda` (
  `Venda_ID` int(11) NOT NULL,
  `Cliente_ID` int(11) NOT NULL,
  `Colaborador_ID` int(11) NOT NULL,
  `Valor_Venda` double NOT NULL,
  `Data_Venda` date NOT NULL,
  `anotacoes` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Despejando dados para a tabela `registrovenda`
--

INSERT INTO `registrovenda` (`Venda_ID`, `Cliente_ID`, `Colaborador_ID`, `Valor_Venda`, `Data_Venda`, `anotacoes`) VALUES
(1, 2, 2, 21998, '2023-06-26', NULL),
(2, 2, 2, 21998, '2023-06-26', 'wewq'),
(3, 5, 2, 6039.85, '2023-06-27', NULL),
(4, 5, 2, 6039.85, '2023-06-27', NULL),
(5, 5, 2, 9599.92, '2023-06-27', 'Teste59'),
(6, 7, 2, 21998, '2023-06-27', 'Teste de alteração da taxa de comissão'),
(7, 7, 2, 262194.3, '2023-06-27', 'Teste de alteração de taxa de comissão 2'),
(8, 5, 2, 349592.4, '2023-06-27', NULL),
(9, 7, 3, 671.8, '2023-06-27', NULL);

-- --------------------------------------------------------

--
-- Estrutura para tabela `registrovenda_item`
--

CREATE TABLE `registrovenda_item` (
  `Venda_ID` int(11) NOT NULL,
  `catalogo_Item_ID` int(11) NOT NULL,
  `Quantidade` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Despejando dados para a tabela `registrovenda_item`
--

INSERT INTO `registrovenda_item` (`Venda_ID`, `catalogo_Item_ID`, `Quantidade`) VALUES
(1, 4, 2),
(2, 4, 2),
(3, 2, 5),
(3, 8, 1),
(4, 2, 5),
(4, 8, 1),
(5, 2, 8),
(6, 4, 2),
(7, 3, 6),
(8, 3, 8),
(9, 11, 1),
(9, 13, 1);

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `catalogo`
--
ALTER TABLE `catalogo`
  ADD PRIMARY KEY (`Item_ID`);

--
-- Índices de tabela `cidade`
--
ALTER TABLE `cidade`
  ADD PRIMARY KEY (`id_Cidade`,`id_Estado`),
  ADD KEY `fk_cidade_estado1_idx` (`id_Estado`);

--
-- Índices de tabela `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`Cliente_ID`,`Endereco_idEndereco`),
  ADD UNIQUE KEY `unique_cliente` (`NomeCliente`,`SobreNomeCliente`,`cnpj_cpf`),
  ADD KEY `fk_Cliente_Endereco1_idx` (`Endereco_idEndereco`);

--
-- Índices de tabela `colaborador`
--
ALTER TABLE `colaborador`
  ADD PRIMARY KEY (`Colaborador_ID`),
  ADD UNIQUE KEY `Nome_Sobrenome_UNIQUE` (`NomeColaborador`,`SobreNomeColab`);

--
-- Índices de tabela `comissao`
--
ALTER TABLE `comissao`
  ADD PRIMARY KEY (`Comissao_ID`,`colaborador_Colaborador_ID`),
  ADD KEY `fk_comissao_colaborador1_idx` (`colaborador_Colaborador_ID`);

--
-- Índices de tabela `endereco`
--
ALTER TABLE `endereco`
  ADD PRIMARY KEY (`idEndereco`,`Cidade_id_Cidade`,`Cidade_id_Estado`),
  ADD KEY `fk_Endereco_Cidade1_idx` (`Cidade_id_Cidade`,`Cidade_id_Estado`);

--
-- Índices de tabela `estado`
--
ALTER TABLE `estado`
  ADD PRIMARY KEY (`id_Estado`);

--
-- Índices de tabela `expalternativas`
--
ALTER TABLE `expalternativas`
  ADD PRIMARY KEY (`expAlternativas_id`,`expperguntas_expPerguntas_id`),
  ADD UNIQUE KEY `unique_alternativa` (`expperguntas_expPerguntas_id`,`Alternativas`),
  ADD KEY `fk_expalternativas_expperguntas1_idx` (`expperguntas_expPerguntas_id`);

--
-- Índices de tabela `experiencia_venda`
--
ALTER TABLE `experiencia_venda`
  ADD PRIMARY KEY (`Experiencia_ID`,`Perguntas_id`,`cliente_Cliente_ID`,`Alternativas_id`,`registrovenda_ID`),
  ADD KEY `fk_experiencia_venda_cliente1_idx` (`cliente_Cliente_ID`),
  ADD KEY `fk_experiencia_venda_expAlternativas1_idx` (`Alternativas_id`,`Perguntas_id`),
  ADD KEY `fk_experiencia_venda_registrovenda1_idx` (`registrovenda_ID`);

--
-- Índices de tabela `expperguntas`
--
ALTER TABLE `expperguntas`
  ADD PRIMARY KEY (`expPerguntas_id`);

--
-- Índices de tabela `registrovenda`
--
ALTER TABLE `registrovenda`
  ADD PRIMARY KEY (`Venda_ID`,`Cliente_ID`,`Colaborador_ID`),
  ADD KEY `fk_RegistroVenda_Cliente1_idx` (`Cliente_ID`),
  ADD KEY `fk_registrovenda_colaborador1_idx` (`Colaborador_ID`);

--
-- Índices de tabela `registrovenda_item`
--
ALTER TABLE `registrovenda_item`
  ADD PRIMARY KEY (`Venda_ID`,`catalogo_Item_ID`),
  ADD KEY `fk_registrovenda_item_catalogo1_idx` (`catalogo_Item_ID`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `catalogo`
--
ALTER TABLE `catalogo`
  MODIFY `Item_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de tabela `cidade`
--
ALTER TABLE `cidade`
  MODIFY `id_Cidade` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de tabela `cliente`
--
ALTER TABLE `cliente`
  MODIFY `Cliente_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;

--
-- AUTO_INCREMENT de tabela `colaborador`
--
ALTER TABLE `colaborador`
  MODIFY `Colaborador_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de tabela `comissao`
--
ALTER TABLE `comissao`
  MODIFY `Comissao_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de tabela `endereco`
--
ALTER TABLE `endereco`
  MODIFY `idEndereco` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT de tabela `estado`
--
ALTER TABLE `estado`
  MODIFY `id_Estado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de tabela `expalternativas`
--
ALTER TABLE `expalternativas`
  MODIFY `expAlternativas_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT de tabela `experiencia_venda`
--
ALTER TABLE `experiencia_venda`
  MODIFY `Experiencia_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT de tabela `expperguntas`
--
ALTER TABLE `expperguntas`
  MODIFY `expPerguntas_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de tabela `registrovenda`
--
ALTER TABLE `registrovenda`
  MODIFY `Venda_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `cidade`
--
ALTER TABLE `cidade`
  ADD CONSTRAINT `fk_cidade_estado1` FOREIGN KEY (`id_Estado`) REFERENCES `estado` (`id_Estado`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Restrições para tabelas `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `fk_Cliente_Endereco1` FOREIGN KEY (`Endereco_idEndereco`) REFERENCES `endereco` (`idEndereco`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Restrições para tabelas `comissao`
--
ALTER TABLE `comissao`
  ADD CONSTRAINT `fk_comissao_colaborador1` FOREIGN KEY (`colaborador_Colaborador_ID`) REFERENCES `colaborador` (`Colaborador_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Restrições para tabelas `endereco`
--
ALTER TABLE `endereco`
  ADD CONSTRAINT `fk_Endereco_Cidade1` FOREIGN KEY (`Cidade_id_Cidade`) REFERENCES `cidade` (`id_Cidade`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Restrições para tabelas `expalternativas`
--
ALTER TABLE `expalternativas`
  ADD CONSTRAINT `fk_expalternativas_expperguntas1` FOREIGN KEY (`expperguntas_expPerguntas_id`) REFERENCES `expperguntas` (`expPerguntas_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Restrições para tabelas `experiencia_venda`
--
ALTER TABLE `experiencia_venda`
  ADD CONSTRAINT `fk_experiencia_venda_cliente1` FOREIGN KEY (`cliente_Cliente_ID`) REFERENCES `cliente` (`Cliente_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_experiencia_venda_expAlternativas1` FOREIGN KEY (`Alternativas_id`) REFERENCES `expalternativas` (`expAlternativas_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_experiencia_venda_registrovenda1` FOREIGN KEY (`registrovenda_ID`) REFERENCES `registrovenda` (`Venda_ID`);

--
-- Restrições para tabelas `registrovenda`
--
ALTER TABLE `registrovenda`
  ADD CONSTRAINT `fk_RegistroVenda_Cliente1` FOREIGN KEY (`Cliente_ID`) REFERENCES `cliente` (`Cliente_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_registrovenda_colaborador1` FOREIGN KEY (`Colaborador_ID`) REFERENCES `colaborador` (`Colaborador_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Restrições para tabelas `registrovenda_item`
--
ALTER TABLE `registrovenda_item`
  ADD CONSTRAINT `fk_registrovenda_item_catalogo1` FOREIGN KEY (`catalogo_Item_ID`) REFERENCES `catalogo` (`Item_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_registrovenda_item_registrovenda1` FOREIGN KEY (`Venda_ID`) REFERENCES `registrovenda` (`Venda_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
