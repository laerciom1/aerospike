# How to

## Install Server
```sh
$ wget -O aerospike.tgz 'https://www.aerospike.com/download/server/latest/artifact/ubuntu18' 
# Replace with your ubuntu version. In my case, 'ubuntu18'
$ tar -xvf aerospike.tgz
$ cd aerospike-server-community-*-ubuntu18
$ sudo ./asinstall # will install the .deb packages
# To start: sudo service aerospike start
# To get status: sudo service aerospike status
# To stop: sudo service aerospike stop
```

## Install AMC
Download the .deb in www.aerospike.com/download/amc then:
Before install, be sure you already have **python (2.6+)**, **gcc** and **python-dev** installed.
If you are missing any of the above, you can install them by running the command:
```sh
sudo apt-get install <package>
```
After that, run:
```sh
$ sudo dpkg -i aerospike-amc-<version>.deb
$ sudo service amc # for instructions
```

## Install Tools (for AQL)
```sh
$ wget -O aerospike-tools.tgz 'https://www.aerospike.com/download/tools/latest/artifact/ubuntu18'
# Replace with your ubuntu version. In my case, 'ubuntu18'
$ tar -xvf aerospike-tools.tgz
$ cd aerospike-tools-*-ubuntu18.04
$ sudo ./asinstall # will install the .deb packages
$ aql --version # will show the installed version of aql
```

## Configure project in Eclipse
Clone the project and open it in Eclipse.
Maybe you'll have to change the JDK version for which you have installed on your machine.
To do this, follow this steps:
```
Right click on the project > Properties > Java Compiler
Uncheck "Use compliance from execution enviroment..." and select your JDK version
Apply and close
```
Now, use Maven to install the dependencies. In this project the only dependency is the Aerospike Java Client itself.
Again, follow this steps:
```
Right click on the project > Run as > Maven Install
```

You're ready to go! (: