CREATE TABLE utilisateurs (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              nom_utilisateur VARCHAR(255) NOT NULL UNIQUE,
                              mot_de_passe VARCHAR(255) NOT NULL,
                              nom VARCHAR(255),
                              prenom VARCHAR(255),
                              email VARCHAR(255) NOT NULL UNIQUE,
                              manager_id BIGINT,
                              CONSTRAINT fk_manager FOREIGN KEY (manager_id) REFERENCES utilisateurs(id)
);
