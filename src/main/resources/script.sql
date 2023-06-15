CREATE TABLE IF NOT EXISTS `Account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `number` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `Role` (
  `id` int(11) NOT NULL,
  `Name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `Provider` (
  `idProvider` int(11) NOT NULL,
  `location` int(11) NOT NULL,
  `Star` int(11) NOT NULL,
  PRIMARY KEY (`idProvider`),
  UNIQUE KEY `idProvider_UNIQUE` (`idProvider`),
  CONSTRAINT `idProvider` FOREIGN KEY (`idProvider`) REFERENCES `Account` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `AccountRole` (
  `idAccount` int(11) DEFAULT NULL,
  `idRole` int(11) DEFAULT NULL,
  KEY `idAccountKey_idx` (`idAccount`),
  KEY `idRoleKey_idx` (`idRole`,`idAccount`),
  CONSTRAINT `idAccount` FOREIGN KEY (`idAccount`) REFERENCES `Account` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `idRole` FOREIGN KEY (`idRole`) REFERENCES `Role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `AccountWaiting` (
  `id` int(11) NOT NULL,
  `location` int(11) NOT NULL,
  `accepted` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  CONSTRAINT `id` FOREIGN KEY (`id`) REFERENCES `Account` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `DateTimeProvider` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idCustumer` int(11) DEFAULT NULL,
  `idProvider` int(11) NOT NULL,
  `Year` int(11) NOT NULL,
  `Month` int(11) NOT NULL,
  `Day` int(11) NOT NULL,
  `Hour` int(11) NOT NULL,
  `Minute` int(11) NOT NULL,
  `Available` int(11) NOT NULL,
  `Accept` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `idCustumer_idx` (`idCustumer`),
  CONSTRAINT `idCustumer` FOREIGN KEY (`idCustumer`) REFERENCES `Account` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `Notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idAccountNotify` int(11) NOT NULL,
  `idDateTimeProvider` int(11) NOT NULL,
  `Notify` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `idAccountNotify_idx` (`idAccountNotify`),
  KEY `idDateTimeProvider_idx` (`idDateTimeProvider`),
  CONSTRAINT `idAccountNotify` FOREIGN KEY (`idAccountNotify`) REFERENCES `Account` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `idDateTimeProvider` FOREIGN KEY (`idDateTimeProvider`) REFERENCES `DateTimeProvider` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

