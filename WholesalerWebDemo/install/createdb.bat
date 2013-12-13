C:\"Program Files"\MySQL\"MySQL Server 5.6"\bin\mysqladmin --host=localhost --user=%2 --password=%3 drop %1 -f
C:\"Program Files"\MySQL\"MySQL Server 5.6"\bin\mysqladmin --host=localhost --user=%2 --password=%3 create %1
C:\"Program Files"\MySQL\"MySQL Server 5.6"\bin\mysql --host=localhost --user=%2 --password=%3 --database=%1 < ebus.sql
