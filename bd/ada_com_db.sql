-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 13, 2023 at 02:21 AM
-- Server version: 8.0.30
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ada_com_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `catalogo`
--

CREATE TABLE `catalogo` (
  `Item_ID` int NOT NULL,
  `Tipo` int NOT NULL,
  `nome` varchar(65) NOT NULL COMMENT 'Nome do serviço ou marca e modelo do produto',
  `categoria` varchar(65) NOT NULL,
  `valor` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `catalogo`
--

INSERT INTO `catalogo` (`Item_ID`, `Tipo`, `nome`, `categoria`, `valor`) VALUES
(1, 1, 'Tp-Link Archer GX90', 'Roteador', '1499.99'),
(2, 1, 'Asus RT-AX82U', 'Roteador', '1199.99'),
(3, 1, 'Samsung 75QN900A', 'TV', '43699.05'),
(4, 1, 'LG 86NANO75SQA', 'TV', '10999.00'),
(5, 1, 'PlayStation 5', 'Console', '5000.00'),
(6, 1, 'Xbox Series X', 'Console', '5000.00'),
(7, 1, 'Xbox Series S', 'Console', '4500.00'),
(8, 2, 'TV 1', 'TV online', '39.90'),
(9, 2, 'TV 2', 'TV online', '59.90'),
(10, 2, 'TV 3', 'TV online', '79.90'),
(11, 2, '100 Mega', 'Internet', '170.90'),
(12, 2, '500 Mega', 'Internet', '300.90'),
(13, 2, '1 Giga', 'Internet', '500.90'),
(14, 2, 'PC', 'Jogos por assinatura', '20.49'),
(15, 2, 'Console', 'Jogos por assinatura', '39.90');

-- --------------------------------------------------------

--
-- Table structure for table `cidade`
--

CREATE TABLE `cidade` (
  `id_Cidade` int NOT NULL,
  `id_Estado` int NOT NULL,
  `Nome_Cidade` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `cidade`
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
-- Table structure for table `cliente`
--

CREATE TABLE `cliente` (
  `Cliente_ID` int NOT NULL,
  `Endereco_idEndereco` int NOT NULL,
  `NomeCliente` varchar(60) NOT NULL,
  `SobreNomeCliente` varchar(60) NOT NULL,
  `cnpj_cpf` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `cliente`
--

INSERT INTO `cliente` (`Cliente_ID`, `Endereco_idEndereco`, `NomeCliente`, `SobreNomeCliente`, `cnpj_cpf`) VALUES
(4, 3, 'Ana', 'Pereira', 33333333333),
(7, 20, 'Carla', 'Costa', 77777777777),
(1, 16, 'João', 'Silva', 44444444444),
(3, 18, 'José', 'Oliveira', 22222222222),
(5, 19, 'Julia', 'Ferreira', 55555555555),
(6, 5, 'Marcos', 'Ribeiro', 66666666666),
(2, 17, 'Maria', 'Santos', 11111111111),
(8, 21, 'Mario', 'Alberto', 88888888888);

-- --------------------------------------------------------

--
-- Table structure for table `colaborador`
--

CREATE TABLE `colaborador` (
  `Colaborador_ID` int NOT NULL,
  `NomeColaborador` varchar(45) NOT NULL,
  `SobreNomeColab` varchar(60) NOT NULL,
  `Nivel` int NOT NULL DEFAULT '1',
  `Usuario` text NOT NULL,
  `Senha` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `colaborador`
--

INSERT INTO `colaborador` (`Colaborador_ID`, `NomeColaborador`, `SobreNomeColab`, `Nivel`, `Usuario`, `Senha`) VALUES
(1, 'João', 'Silva', 2, 'gestor', '123'),
(2, 'Oliveira', 'Eliel', 1, 'eliel.oliveira', '123'),
(3, 'Paterno', 'Fernanda', 1, 'fernanda.paterno', '123'),
(4, 'Carlos', 'Lenon', 1, 'carlos.lenon', '123'),
(5, 'Alissa', 'Kenbler', 1, 'alissa.kenbler', '123'),
(6, 'Leonardo', 'lima', 1, 'leonardo.lima', '123'),
(7, 'Jackson', 'Barbosa', 1, 'jackson.barbosa', '123'),
(8, 'Lima', 'Bevenutti', 1, 'lima.bevenutti', '123'),
(9, 'Leandra', 'Justus', 1, 'leandra.justus', '123'),
(10, 'Claudia', 'Ketlin', 1, 'claudia.ketlin', '123');

-- --------------------------------------------------------

--
-- Table structure for table `comissao`
--

CREATE TABLE `comissao` (
  `Comissao_ID` int NOT NULL,
  `colaborador_Colaborador_ID` int NOT NULL,
  `TaxaComissao` double NOT NULL,
  `Data` date NOT NULL,
  `Valor_comissao` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `comissao`
--

INSERT INTO `comissao` (`Comissao_ID`, `colaborador_Colaborador_ID`, `TaxaComissao`, `Data`, `Valor_comissao`) VALUES
(1, 2, 0.03, '2023-06-26', '659.94'),
(2, 2, 0.03, '2023-06-27', '288.00'),
(3, 2, 0.03, '2023-06-27', '659.94'),
(4, 2, 0.05, '2023-06-27', '13109.72'),
(5, 2, 0.05, '2023-06-27', '17479.62'),
(6, 3, 0.03, '2023-06-27', '20.15'),
(7, 1, 0.05, '2023-07-07', '22599.52');

-- --------------------------------------------------------

--
-- Table structure for table `endereco`
--

CREATE TABLE `endereco` (
  `idEndereco` int NOT NULL,
  `Cidade_id_Cidade` int NOT NULL,
  `Cidade_id_Estado` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `endereco`
--

INSERT INTO `endereco` (`idEndereco`, `Cidade_id_Cidade`, `Cidade_id_Estado`) VALUES
(1, 1, 1),
(16, 1, 1),
(2, 2, 1),
(17, 2, 1),
(3, 3, 1),
(18, 3, 1),
(4, 4, 1),
(5, 5, 2),
(21, 5, 2),
(6, 6, 2),
(19, 6, 2),
(7, 7, 2),
(8, 8, 2),
(20, 8, 2);

-- --------------------------------------------------------

--
-- Table structure for table `estado`
--

CREATE TABLE `estado` (
  `id_Estado` int NOT NULL,
  `Nome_estado` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `estado`
--

INSERT INTO `estado` (`id_Estado`, `Nome_estado`) VALUES
(1, 'Santa Catarina'),
(2, 'São Paulo');

-- --------------------------------------------------------

--
-- Table structure for table `expalternativas`
--

CREATE TABLE `expalternativas` (
  `expAlternativas_id` int NOT NULL,
  `expperguntas_expPerguntas_id` int NOT NULL,
  `Alternativas` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `expalternativas`
--

INSERT INTO `expalternativas` (`expAlternativas_id`, `expperguntas_expPerguntas_id`, `Alternativas`) VALUES
(4, 1, 'Em um evento de Tecnologia e entretenimento.'),
(18, 1, 'Não quero responder.'),
(3, 1, 'Pesquisando online.'),
(1, 1, 'Post no Instagram.'),
(2, 1, 'Recomendação de conhecidos.'),
(19, 2, 'Não quero responder.'),
(8, 2, 'Pesquisar serviços de internet e jogos por assinatura.'),
(17, 2, 'Post no Instagram.'),
(6, 2, 'Verificar área de atuação da empresa.'),
(7, 2, 'Verificar canais do pacote TV e seus adicionais.'),
(5, 2, 'Verificar preço e formas de compra.'),
(10, 3, 'Conhecia, pois pessoas próximas são clientes.'),
(11, 3, 'Sou cliente em um serviço específico.'),
(9, 3, 'Vi apenas em uma publicidade.'),
(20, 4, 'Não quero responder.'),
(15, 4, 'Não.'),
(13, 4, 'Sim, em uma feira tecnologia e entretenimento.'),
(12, 4, 'Sim, Festa de aniversario da cidade.'),
(14, 4, 'Sim, onde a empresa era um dos patrocinadores.');

-- --------------------------------------------------------

--
-- Table structure for table `experiencia_venda`
--

CREATE TABLE `experiencia_venda` (
  `Experiencia_ID` int NOT NULL,
  `Perguntas_id` int NOT NULL,
  `cliente_Cliente_ID` int NOT NULL,
  `Alternativas_id` int NOT NULL,
  `registrovenda_ID` int NOT NULL,
  `Data` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `experiencia_venda`
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
(27, 4, 7, 13, 9, NULL),
(28, 1, 7, 3, 10, NULL),
(29, 2, 7, 17, 10, NULL),
(30, 3, 7, 10, 10, NULL),
(31, 4, 7, 13, 10, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `expperguntas`
--

CREATE TABLE `expperguntas` (
  `expPerguntas_id` int NOT NULL,
  `Pergunta` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `expperguntas`
--

INSERT INTO `expperguntas` (`expPerguntas_id`, `Pergunta`) VALUES
(1, 'Como você conheceu nossa empresa/produto/serviço?'),
(2, 'Qual foi o motivo que o levou a visitar nosso site?'),
(3, 'Como você descreveria sua familiaridade com nossa empresa antes de visitar nosso site?'),
(4, 'Participou de um evento que a empresa estava presente?');

-- --------------------------------------------------------

--
-- Table structure for table `registrovenda`
--

CREATE TABLE `registrovenda` (
  `Venda_ID` int NOT NULL,
  `Cliente_ID` int NOT NULL,
  `Colaborador_ID` int NOT NULL,
  `Valor_Venda` double NOT NULL,
  `Data_Venda` date NOT NULL,
  `anotacoes` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `registrovenda`
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
(9, 7, 3, 671.8, '2023-06-27', NULL),
(10, 7, 1, 451990.4, '2023-07-07', 'Cliente ama a empresa!');

-- --------------------------------------------------------

--
-- Table structure for table `registrovenda_item`
--

CREATE TABLE `registrovenda_item` (
  `Venda_ID` int NOT NULL,
  `catalogo_Item_ID` int NOT NULL,
  `Quantidade` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Dumping data for table `registrovenda_item`
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
(9, 13, 1),
(10, 1, 10),
(10, 3, 10);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `catalogo`
--
ALTER TABLE `catalogo`
  ADD PRIMARY KEY (`Item_ID`);

--
-- Indexes for table `cidade`
--
ALTER TABLE `cidade`
  ADD PRIMARY KEY (`id_Cidade`,`id_Estado`),
  ADD KEY `fk_cidade_estado1_idx` (`id_Estado`);

--
-- Indexes for table `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`Cliente_ID`,`Endereco_idEndereco`),
  ADD UNIQUE KEY `unique_cliente` (`NomeCliente`,`SobreNomeCliente`,`cnpj_cpf`),
  ADD KEY `fk_Cliente_Endereco1_idx` (`Endereco_idEndereco`);

--
-- Indexes for table `colaborador`
--
ALTER TABLE `colaborador`
  ADD PRIMARY KEY (`Colaborador_ID`),
  ADD UNIQUE KEY `Nome_Sobrenome_UNIQUE` (`NomeColaborador`,`SobreNomeColab`);

--
-- Indexes for table `comissao`
--
ALTER TABLE `comissao`
  ADD PRIMARY KEY (`Comissao_ID`,`colaborador_Colaborador_ID`),
  ADD KEY `fk_comissao_colaborador1_idx` (`colaborador_Colaborador_ID`);

--
-- Indexes for table `endereco`
--
ALTER TABLE `endereco`
  ADD PRIMARY KEY (`idEndereco`,`Cidade_id_Cidade`,`Cidade_id_Estado`),
  ADD KEY `fk_Endereco_Cidade1_idx` (`Cidade_id_Cidade`,`Cidade_id_Estado`);

--
-- Indexes for table `estado`
--
ALTER TABLE `estado`
  ADD PRIMARY KEY (`id_Estado`);

--
-- Indexes for table `expalternativas`
--
ALTER TABLE `expalternativas`
  ADD PRIMARY KEY (`expAlternativas_id`,`expperguntas_expPerguntas_id`),
  ADD UNIQUE KEY `unique_alternativa` (`expperguntas_expPerguntas_id`,`Alternativas`),
  ADD KEY `fk_expalternativas_expperguntas1_idx` (`expperguntas_expPerguntas_id`);

--
-- Indexes for table `experiencia_venda`
--
ALTER TABLE `experiencia_venda`
  ADD PRIMARY KEY (`Experiencia_ID`,`Perguntas_id`,`cliente_Cliente_ID`,`Alternativas_id`,`registrovenda_ID`),
  ADD KEY `fk_experiencia_venda_cliente1_idx` (`cliente_Cliente_ID`),
  ADD KEY `fk_experiencia_venda_expAlternativas1_idx` (`Alternativas_id`,`Perguntas_id`),
  ADD KEY `fk_experiencia_venda_registrovenda1_idx` (`registrovenda_ID`);

--
-- Indexes for table `expperguntas`
--
ALTER TABLE `expperguntas`
  ADD PRIMARY KEY (`expPerguntas_id`);

--
-- Indexes for table `registrovenda`
--
ALTER TABLE `registrovenda`
  ADD PRIMARY KEY (`Venda_ID`,`Cliente_ID`,`Colaborador_ID`),
  ADD KEY `fk_RegistroVenda_Cliente1_idx` (`Cliente_ID`),
  ADD KEY `fk_registrovenda_colaborador1_idx` (`Colaborador_ID`);

--
-- Indexes for table `registrovenda_item`
--
ALTER TABLE `registrovenda_item`
  ADD PRIMARY KEY (`Venda_ID`,`catalogo_Item_ID`),
  ADD KEY `fk_registrovenda_item_catalogo1_idx` (`catalogo_Item_ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `catalogo`
--
ALTER TABLE `catalogo`
  MODIFY `Item_ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `cidade`
--
ALTER TABLE `cidade`
  MODIFY `id_Cidade` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `cliente`
--
ALTER TABLE `cliente`
  MODIFY `Cliente_ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `colaborador`
--
ALTER TABLE `colaborador`
  MODIFY `Colaborador_ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `comissao`
--
ALTER TABLE `comissao`
  MODIFY `Comissao_ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `endereco`
--
ALTER TABLE `endereco`
  MODIFY `idEndereco` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `estado`
--
ALTER TABLE `estado`
  MODIFY `id_Estado` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `expalternativas`
--
ALTER TABLE `expalternativas`
  MODIFY `expAlternativas_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `experiencia_venda`
--
ALTER TABLE `experiencia_venda`
  MODIFY `Experiencia_ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT for table `expperguntas`
--
ALTER TABLE `expperguntas`
  MODIFY `expPerguntas_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `registrovenda`
--
ALTER TABLE `registrovenda`
  MODIFY `Venda_ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cidade`
--
ALTER TABLE `cidade`
  ADD CONSTRAINT `fk_cidade_estado1` FOREIGN KEY (`id_Estado`) REFERENCES `estado` (`id_Estado`);

--
-- Constraints for table `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `fk_Cliente_Endereco1` FOREIGN KEY (`Endereco_idEndereco`) REFERENCES `endereco` (`idEndereco`);

--
-- Constraints for table `comissao`
--
ALTER TABLE `comissao`
  ADD CONSTRAINT `fk_comissao_colaborador1` FOREIGN KEY (`colaborador_Colaborador_ID`) REFERENCES `colaborador` (`Colaborador_ID`);

--
-- Constraints for table `endereco`
--
ALTER TABLE `endereco`
  ADD CONSTRAINT `fk_Endereco_Cidade1` FOREIGN KEY (`Cidade_id_Cidade`) REFERENCES `cidade` (`id_Cidade`);

--
-- Constraints for table `expalternativas`
--
ALTER TABLE `expalternativas`
  ADD CONSTRAINT `fk_expalternativas_expperguntas1` FOREIGN KEY (`expperguntas_expPerguntas_id`) REFERENCES `expperguntas` (`expPerguntas_id`);

--
-- Constraints for table `experiencia_venda`
--
ALTER TABLE `experiencia_venda`
  ADD CONSTRAINT `fk_experiencia_venda_cliente1` FOREIGN KEY (`cliente_Cliente_ID`) REFERENCES `cliente` (`Cliente_ID`),
  ADD CONSTRAINT `fk_experiencia_venda_expAlternativas1` FOREIGN KEY (`Alternativas_id`) REFERENCES `expalternativas` (`expAlternativas_id`),
  ADD CONSTRAINT `fk_experiencia_venda_registrovenda1` FOREIGN KEY (`registrovenda_ID`) REFERENCES `registrovenda` (`Venda_ID`);

--
-- Constraints for table `registrovenda`
--
ALTER TABLE `registrovenda`
  ADD CONSTRAINT `fk_RegistroVenda_Cliente1` FOREIGN KEY (`Cliente_ID`) REFERENCES `cliente` (`Cliente_ID`),
  ADD CONSTRAINT `fk_registrovenda_colaborador1` FOREIGN KEY (`Colaborador_ID`) REFERENCES `colaborador` (`Colaborador_ID`);

--
-- Constraints for table `registrovenda_item`
--
ALTER TABLE `registrovenda_item`
  ADD CONSTRAINT `fk_registrovenda_item_catalogo1` FOREIGN KEY (`catalogo_Item_ID`) REFERENCES `catalogo` (`Item_ID`),
  ADD CONSTRAINT `fk_registrovenda_item_registrovenda1` FOREIGN KEY (`Venda_ID`) REFERENCES `registrovenda` (`Venda_ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
