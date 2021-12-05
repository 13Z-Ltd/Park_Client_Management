


<h1 align="center">Hi 👋, I'm Zoltan Balla</h1>
<h3 align="center">A currently a freelance graduate software developer from Hungary</h3>


<h3 align="center"># Park_Client_Management</h3>
This is my last complete application which is do a full client management for an adventure park.


The aim of the program was to admitt the guests whoes entering the park with the help of barcode wristbands,
and to follow their activities during their stay there.
The system records the time of each guest's departure, when they were on which track, and the details of the 
use of extra services witch are available in the park. It has be used to determine the amount to be paid at the end of the visit.
It also records inappropriate times for visiting the park, which may lead to the re-use of the tickets concerned.
Distinguishes between adult, junior and child tickets.

The program collects the data in a central database available on the local network, which can be used by any number of client machines at once.
At check-in, you can choose between ticket issuance and ticket redemption or administration interfaces. 


#Instructions to run it!

To use the application, you must create a PostgreSQL database accessible from the local network.
And authorized to process and service requests from clients.
Then in the program's "hibernate.cfg.xml" file (located at \cfg\hibernate.cfg.xml and \src\hibernate.cfg.xml)
you must enter the name of the database in the line of: 
<property name="hibernate.connection.url">jdbc:postgresql://localhost:5432/<!--Put your database name here!--></property>
And the authorized user name in the line of:
<property name="hibernate.connection.username"><!--Put your authorized user name here!--></property>
And the password in the line of:
<property name="hibernate.connection.password"><!--Put your password here!--></property>

If the program does not access the database at the specified ip address, it provides an option in an additional pop-up part
at the bottom of the login screen to enter the correct ip address exactly and then recheck the connection. 

<h3 align="left">Connect with me:</h3>
<p align="left">
</p>

<h3 align="left">Languages and Tools:</h3>
<p align="left"> <a href="https://developer.android.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/android/android-original-wordmark.svg" alt="android" width="40" height="40"/> </a> <a href="https://www.w3schools.com/cpp/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/cplusplus/cplusplus-original.svg" alt="cplusplus" width="40" height="40"/> </a> <a href="https://dotnet.microsoft.com/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/dot-net/dot-net-original-wordmark.svg" alt="dotnet" width="40" height="40"/> </a> <a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/> </a> <a href="https://mariadb.org/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/mariadb/mariadb-icon.svg" alt="mariadb" width="40" height="40"/> </a> <a href="https://www.microsoft.com/en-us/sql-server" target="_blank" rel="noreferrer"> <img src="https://www.svgrepo.com/show/303229/microsoft-sql-server-logo.svg" alt="mssql" width="40" height="40"/> </a> <a href="https://www.mysql.com/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/mysql/mysql-original-wordmark.svg" alt="mysql" width="40" height="40"/> </a> <a href="https://www.postgresql.org" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/postgresql/postgresql-original-wordmark.svg" alt="postgresql" width="40" height="40"/> </a> <a href="https://www.python.org" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/python/python-original.svg" alt="python" width="40" height="40"/> </a> <a href="https://spring.io/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="40" height="40"/> </a> <a href="https://www.sqlite.org/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/sqlite/sqlite-icon.svg" alt="sqlite" width="40" height="40"/> </a> </p>

