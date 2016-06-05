DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `procReplaceCPF`(
	IN ID_IN LONG,
    OUT CPF_OUT VARCHAR(15)
)
BEGIN
	SELECT
		REPLACE(REPLACE(CPF, '.', ''), '-', '')
    INTO
		CPF_OUT
    FROM
		DOCUMENT
    WHERE
		ID = ID_IN;
END$$
DELIMITER ;
