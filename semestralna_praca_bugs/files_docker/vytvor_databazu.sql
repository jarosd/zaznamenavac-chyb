CREATE USER 'docker' IDENTIFIED BY 'docker';

CREATE DATABASE vpa_jarosd_bugs;

GRANT ALL PRIVILEGES ON vpa_jarosd_bugs.* TO 'docker';
FLUSH PRIVILEGES;
