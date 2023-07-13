CREATE DATABASE  IF NOT EXISTS `ada_com_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ada_com_db`;
-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: ada_com_db
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `catalogo`
--

DROP TABLE IF EXISTS `catalogo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `catalogo` (
  `Item_ID` int NOT NULL AUTO_INCREMENT,
  `Tipo` int NOT NULL,
  `nome` varchar(65) NOT NULL COMMENT 'Nome do serviço ou marca e modelo do produto',
  `categoria` varchar(65) NOT NULL,
  `valor` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`Item_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `catalogo`
--

LOCK TABLES `catalogo` WRITE;
/*!40000 ALTER TABLE `catalogo` DISABLE KEYS */;
INSERT INTO `catalogo` VALUES (1,1,'Tp-Link Archer GX90','Roteador',1499.99),(2,1,'Asus RT-AX82U','Roteador',1199.99),(3,1,'Samsung 75QN900A','TV',43699.05),(4,1,'LG 86NANO75SQA','TV',10999.00),(5,1,'PlayStation 5','Console',5000.00),(6,1,'Xbox Series X','Console',5000.00),(7,1,'Xbox Series S','Console',4500.00),(8,2,'TV 1','TV online',39.90),(9,2,'TV 2','TV online',59.90),(10,2,'TV 3','TV online',79.90),(11,2,'100 Mega','Internet',170.90),(12,2,'500 Mega','Internet',300.90),(13,2,'1 Giga','Internet',500.90),(14,2,'PC','Jogos por assinatura',20.49),(15,2,'Console','Jogos por assinatura',39.90);
/*!40000 ALTER TABLE `catalogo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cidade`
--

DROP TABLE IF EXISTS `cidade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cidade` (
  `id_Cidade` int NOT NULL AUTO_INCREMENT,
  `id_Estado` int NOT NULL,
  `Nome_Cidade` varchar(45) NOT NULL,
  PRIMARY KEY (`id_Cidade`,`id_Estado`),
  KEY `fk_cidade_estado1_idx` (`id_Estado`),
  CONSTRAINT `fk_cidade_estado1` FOREIGN KEY (`id_Estado`) REFERENCES `estado` (`id_Estado`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cidade`
--

LOCK TABLES `cidade` WRITE;
/*!40000 ALTER TABLE `cidade` DISABLE KEYS */;
INSERT INTO `cidade` VALUES (1,1,'Florianópolis'),(2,1,'Joinville'),(3,1,'Blumenau'),(4,1,'São José'),(5,2,'São Paulo'),(6,2,'Campinas'),(7,2,'Santos'),(8,2,'Ribeirão Preto');
/*!40000 ALTER TABLE `cidade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `Cliente_ID` int NOT NULL AUTO_INCREMENT,
  `Endereco_idEndereco` int NOT NULL,
  `NomeCliente` varchar(60) NOT NULL,
  `SobreNomeCliente` varchar(60) NOT NULL,
  `cnpj_cpf` bigint DEFAULT NULL,
  PRIMARY KEY (`Cliente_ID`,`Endereco_idEndereco`),
  UNIQUE KEY `unique_cliente` (`NomeCliente`,`SobreNomeCliente`,`cnpj_cpf`),
  KEY `fk_Cliente_Endereco1_idx` (`Endereco_idEndereco`),
  CONSTRAINT `fk_Cliente_Endereco1` FOREIGN KEY (`Endereco_idEndereco`) REFERENCES `endereco` (`idEndereco`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (4,3,'Ana','Pereira',33333333333),(7,20,'Carla','Costa',77777777777),(1,16,'João','Silva',44444444444),(3,18,'José','Oliveira',22222222222),(5,19,'Julia','Ferreira',55555555555),(6,5,'Marcos','Ribeiro',66666666666),(2,17,'Maria','Santos',11111111111),(8,21,'Mario','Alberto',88888888888);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `colaborador`
--

DROP TABLE IF EXISTS `colaborador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `colaborador` (
  `Colaborador_ID` int NOT NULL AUTO_INCREMENT,
  `NomeColaborador` varchar(45) NOT NULL,
  `SobreNomeColab` varchar(60) NOT NULL,
  `Nivel` int NOT NULL DEFAULT '1',
  `Usuario` text NOT NULL,
  `Senha` text NOT NULL,
  PRIMARY KEY (`Colaborador_ID`),
  UNIQUE KEY `Nome_Sobrenome_UNIQUE` (`NomeColaborador`,`SobreNomeColab`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `colaborador`
--

LOCK TABLES `colaborador` WRITE;
/*!40000 ALTER TABLE `colaborador` DISABLE KEYS */;
INSERT INTO `colaborador` VALUES (1,'João','Silva',2,'gestor','123'),(2,'Oliveira','Eliel',1,'eliel.oliveira','123'),(3,'Paterno','Fernanda',1,'fernanda.paterno','123'),(4,'Carlos','Lenon',1,'carlos.lenon','123'),(5,'Alissa','Kenbler',1,'alissa.kenbler','123'),(6,'Leonardo','lima',1,'leonardo.lima','123'),(7,'Jackson','Barbosa',1,'jackson.barbosa','123'),(8,'Lima','Bevenutti',1,'lima.bevenutti','123'),(9,'Leandra','Justus',1,'leandra.justus','123'),(10,'Claudia','Ketlin',1,'claudia.ketlin','123');
/*!40000 ALTER TABLE `colaborador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comissao`
--

DROP TABLE IF EXISTS `comissao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comissao` (
  `Comissao_ID` int NOT NULL AUTO_INCREMENT,
  `colaborador_Colaborador_ID` int NOT NULL,
  `TaxaComissao` double NOT NULL,
  `Data` date NOT NULL,
  `Valor_comissao` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`Comissao_ID`,`colaborador_Colaborador_ID`),
  KEY `fk_comissao_colaborador1_idx` (`colaborador_Colaborador_ID`),
  CONSTRAINT `fk_comissao_colaborador1` FOREIGN KEY (`colaborador_Colaborador_ID`) REFERENCES `colaborador` (`Colaborador_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comissao`
--

LOCK TABLES `comissao` WRITE;
/*!40000 ALTER TABLE `comissao` DISABLE KEYS */;
INSERT INTO `comissao` VALUES (1,2,0.03,'2023-06-26',659.94),(2,2,0.03,'2023-06-27',288.00),(3,2,0.03,'2023-06-27',659.94),(4,2,0.05,'2023-06-27',13109.72),(5,2,0.05,'2023-06-27',17479.62),(6,3,0.03,'2023-06-27',20.15),(7,1,0.05,'2023-07-07',22599.52);
/*!40000 ALTER TABLE `comissao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `endereco`
--

DROP TABLE IF EXISTS `endereco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `endereco` (
  `idEndereco` int NOT NULL AUTO_INCREMENT,
  `Cidade_id_Cidade` int NOT NULL,
  `Cidade_id_Estado` int NOT NULL,
  PRIMARY KEY (`idEndereco`,`Cidade_id_Cidade`,`Cidade_id_Estado`),
  KEY `fk_Endereco_Cidade1_idx` (`Cidade_id_Cidade`,`Cidade_id_Estado`),
  CONSTRAINT `fk_Endereco_Cidade1` FOREIGN KEY (`Cidade_id_Cidade`) REFERENCES `cidade` (`id_Cidade`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `endereco`
--

LOCK TABLES `endereco` WRITE;
/*!40000 ALTER TABLE `endereco` DISABLE KEYS */;
INSERT INTO `endereco` VALUES (1,1,1),(16,1,1),(2,2,1),(17,2,1),(3,3,1),(18,3,1),(4,4,1),(5,5,2),(21,5,2),(6,6,2),(19,6,2),(7,7,2),(8,8,2),(20,8,2);
/*!40000 ALTER TABLE `endereco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estado`
--

DROP TABLE IF EXISTS `estado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estado` (
  `id_Estado` int NOT NULL AUTO_INCREMENT,
  `Nome_estado` varchar(45) NOT NULL,
  PRIMARY KEY (`id_Estado`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estado`
--

LOCK TABLES `estado` WRITE;
/*!40000 ALTER TABLE `estado` DISABLE KEYS */;
INSERT INTO `estado` VALUES (1,'Santa Catarina'),(2,'São Paulo');
/*!40000 ALTER TABLE `estado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expalternativas`
--

DROP TABLE IF EXISTS `expalternativas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `expalternativas` (
  `expAlternativas_id` int NOT NULL AUTO_INCREMENT,
  `expperguntas_expPerguntas_id` int NOT NULL,
  `Alternativas` varchar(255) NOT NULL,
  PRIMARY KEY (`expAlternativas_id`,`expperguntas_expPerguntas_id`),
  UNIQUE KEY `unique_alternativa` (`expperguntas_expPerguntas_id`,`Alternativas`),
  KEY `fk_expalternativas_expperguntas1_idx` (`expperguntas_expPerguntas_id`),
  CONSTRAINT `fk_expalternativas_expperguntas1` FOREIGN KEY (`expperguntas_expPerguntas_id`) REFERENCES `expperguntas` (`expPerguntas_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expalternativas`
--

LOCK TABLES `expalternativas` WRITE;
/*!40000 ALTER TABLE `expalternativas` DISABLE KEYS */;
INSERT INTO `expalternativas` VALUES (4,1,'Em um evento de Tecnologia e entretenimento.'),(18,1,'Não quero responder.'),(3,1,'Pesquisando online.'),(1,1,'Post no Instagram.'),(2,1,'Recomendação de conhecidos.'),(19,2,'Não quero responder.'),(8,2,'Pesquisar serviços de internet e jogos por assinatura.'),(17,2,'Post no Instagram.'),(6,2,'Verificar área de atuação da empresa.'),(7,2,'Verificar canais do pacote TV e seus adicionais.'),(5,2,'Verificar preço e formas de compra.'),(10,3,'Conhecia, pois pessoas próximas são clientes.'),(11,3,'Sou cliente em um serviço específico.'),(9,3,'Vi apenas em uma publicidade.'),(20,4,'Não quero responder.'),(15,4,'Não.'),(13,4,'Sim, em uma feira tecnologia e entretenimento.'),(12,4,'Sim, Festa de aniversario da cidade.'),(14,4,'Sim, onde a empresa era um dos patrocinadores.');
/*!40000 ALTER TABLE `expalternativas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `experiencia_venda`
--

DROP TABLE IF EXISTS `experiencia_venda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `experiencia_venda` (
  `Experiencia_ID` int NOT NULL AUTO_INCREMENT,
  `Perguntas_id` int NOT NULL,
  `cliente_Cliente_ID` int NOT NULL,
  `Alternativas_id` int NOT NULL,
  `registrovenda_ID` int NOT NULL,
  `Data` date DEFAULT NULL,
  PRIMARY KEY (`Experiencia_ID`,`Perguntas_id`,`cliente_Cliente_ID`,`Alternativas_id`,`registrovenda_ID`),
  KEY `fk_experiencia_venda_cliente1_idx` (`cliente_Cliente_ID`),
  KEY `fk_experiencia_venda_expAlternativas1_idx` (`Alternativas_id`,`Perguntas_id`),
  KEY `fk_experiencia_venda_registrovenda1_idx` (`registrovenda_ID`),
  CONSTRAINT `fk_experiencia_venda_cliente1` FOREIGN KEY (`cliente_Cliente_ID`) REFERENCES `cliente` (`Cliente_ID`),
  CONSTRAINT `fk_experiencia_venda_expAlternativas1` FOREIGN KEY (`Alternativas_id`) REFERENCES `expalternativas` (`expAlternativas_id`),
  CONSTRAINT `fk_experiencia_venda_registrovenda1` FOREIGN KEY (`registrovenda_ID`) REFERENCES `registrovenda` (`Venda_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `experiencia_venda`
--

LOCK TABLES `experiencia_venda` WRITE;
/*!40000 ALTER TABLE `experiencia_venda` DISABLE KEYS */;
INSERT INTO `experiencia_venda` VALUES (2,1,2,1,2,NULL),(3,2,2,17,2,NULL),(4,3,2,11,2,NULL),(5,4,2,15,2,NULL),(8,1,5,3,5,NULL),(9,2,5,6,5,NULL),(10,3,5,9,5,NULL),(11,4,5,15,5,NULL),(12,1,7,1,6,NULL),(13,2,7,7,6,NULL),(14,3,7,9,6,NULL),(15,4,7,13,6,NULL),(16,1,7,1,7,NULL),(17,2,7,8,7,NULL),(18,3,7,9,7,NULL),(19,4,7,12,7,NULL),(20,1,5,1,8,NULL),(21,2,5,6,8,NULL),(22,3,5,11,8,NULL),(23,4,5,15,8,NULL),(24,1,7,1,9,NULL),(25,2,7,7,9,NULL),(26,3,7,9,9,NULL),(27,4,7,13,9,NULL),(28,1,7,3,10,NULL),(29,2,7,17,10,NULL),(30,3,7,10,10,NULL),(31,4,7,13,10,NULL);
/*!40000 ALTER TABLE `experiencia_venda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expperguntas`
--

DROP TABLE IF EXISTS `expperguntas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `expperguntas` (
  `expPerguntas_id` int NOT NULL AUTO_INCREMENT,
  `Pergunta` varchar(255) NOT NULL,
  PRIMARY KEY (`expPerguntas_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expperguntas`
--

LOCK TABLES `expperguntas` WRITE;
/*!40000 ALTER TABLE `expperguntas` DISABLE KEYS */;
INSERT INTO `expperguntas` VALUES (1,'Como você conheceu nossa empresa/produto/serviço?'),(2,'Qual foi o motivo que o levou a visitar nosso site?'),(3,'Como você descreveria sua familiaridade com nossa empresa antes de visitar nosso site?'),(4,'Participou de um evento que a empresa estava presente?');
/*!40000 ALTER TABLE `expperguntas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registrovenda`
--

DROP TABLE IF EXISTS `registrovenda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registrovenda` (
  `Venda_ID` int NOT NULL AUTO_INCREMENT,
  `Cliente_ID` int NOT NULL,
  `Colaborador_ID` int NOT NULL,
  `Valor_Venda` double NOT NULL,
  `Data_Venda` date NOT NULL,
  `anotacoes` text,
  PRIMARY KEY (`Venda_ID`,`Cliente_ID`,`Colaborador_ID`),
  KEY `fk_RegistroVenda_Cliente1_idx` (`Cliente_ID`),
  KEY `fk_registrovenda_colaborador1_idx` (`Colaborador_ID`),
  CONSTRAINT `fk_RegistroVenda_Cliente1` FOREIGN KEY (`Cliente_ID`) REFERENCES `cliente` (`Cliente_ID`),
  CONSTRAINT `fk_registrovenda_colaborador1` FOREIGN KEY (`Colaborador_ID`) REFERENCES `colaborador` (`Colaborador_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registrovenda`
--

LOCK TABLES `registrovenda` WRITE;
/*!40000 ALTER TABLE `registrovenda` DISABLE KEYS */;
INSERT INTO `registrovenda` VALUES (1,2,2,21998,'2023-06-26',NULL),(2,2,2,21998,'2023-06-26','wewq'),(3,5,2,6039.85,'2023-06-27',NULL),(4,5,2,6039.85,'2023-06-27',NULL),(5,5,2,9599.92,'2023-06-27','Teste59'),(6,7,2,21998,'2023-06-27','Teste de alteração da taxa de comissão'),(7,7,2,262194.3,'2023-06-27','Teste de alteração de taxa de comissão 2'),(8,5,2,349592.4,'2023-06-27',NULL),(9,7,3,671.8,'2023-06-27',NULL),(10,7,1,451990.4,'2023-07-07','Cliente ama a empresa!');
/*!40000 ALTER TABLE `registrovenda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registrovenda_item`
--

DROP TABLE IF EXISTS `registrovenda_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registrovenda_item` (
  `Venda_ID` int NOT NULL,
  `catalogo_Item_ID` int NOT NULL,
  `Quantidade` int NOT NULL,
  PRIMARY KEY (`Venda_ID`,`catalogo_Item_ID`),
  KEY `fk_registrovenda_item_catalogo1_idx` (`catalogo_Item_ID`),
  CONSTRAINT `fk_registrovenda_item_catalogo1` FOREIGN KEY (`catalogo_Item_ID`) REFERENCES `catalogo` (`Item_ID`),
  CONSTRAINT `fk_registrovenda_item_registrovenda1` FOREIGN KEY (`Venda_ID`) REFERENCES `registrovenda` (`Venda_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registrovenda_item`
--

LOCK TABLES `registrovenda_item` WRITE;
/*!40000 ALTER TABLE `registrovenda_item` DISABLE KEYS */;
INSERT INTO `registrovenda_item` VALUES (1,4,2),(2,4,2),(3,2,5),(3,8,1),(4,2,5),(4,8,1),(5,2,8),(6,4,2),(7,3,6),(8,3,8),(9,11,1),(9,13,1),(10,1,10),(10,3,10);
/*!40000 ALTER TABLE `registrovenda_item` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-12 21:32:09
