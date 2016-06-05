DELIMITER $$
CREATE DEFINER=`root`@`localhost` FUNCTION `funcConcatAddress`(ID_IN long) RETURNS varchar(255) CHARSET utf8
BEGIN
	DECLARE result, _city, _street varchar(255);
    
    SELECT city, street
    INTO _city, _street
    FROM address
    WHERE id = ID_IN;
    
    SET result = CONCAT(_city, " - ", _street);
RETURN result;
END$$
DELIMITER ;