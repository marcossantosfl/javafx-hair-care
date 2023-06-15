DELIMITER //

CREATE TRIGGER after_update_datetime
AFTER UPDATE
ON DateTimeProvider FOR EACH ROW
BEGIN
IF (new.Accept = 1) THEN
    INSERT INTO Notification(idAccountNotify,idDateTimeProvider,Notify) VALUES(old.idCustumer, old.id, 1);
    END IF;
END$$ 

DELIMITER ;

DELIMITER $$

CREATE TRIGGER after_waiting_update
AFTER UPDATE
ON AccountWaiting FOR EACH ROW
BEGIN
        INSERT INTO Provider(idProvider,location)
        VALUES(old.id, old.location);
		INSERT INTO Review(idAccountReview,IdProviderReview,StarGiven)
        VALUES(old.id, old.id, 1);
END$$ 

DELIMITER ;

DELIMITER //

CREATE TRIGGER after_waiting_delete
BEFORE DELETE
ON AccountWaiting FOR EACH ROW
BEGIN
    UPDATE AccountRole SET idRole = 0 WHERE idAccount = old.id;
END$$ 

DELIMITER ;