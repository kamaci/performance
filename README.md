This is the readme file for performance comparison of multithreading in Java. For analysis and conclusion of results read here: http://furkankamaci.com/performance-comparison-of-multithreading-in-java/

#How To Compile Application?

Project is developed as a maven project. So, 

	mvn clean install

will be enough. There are also maven plugins for downloading licenses and analyzing application code with Sonar and they can be run individually.

#How To Run Application?

Application has a command line interface which will guide you to run it. When you navigate to
distribution folder or target/performance-1.0 folder (not directly under target folder) you can use that
instructions:

	java -jar performance-{version}.jar -ms [MATRIX SIZES] -tc [THREAD COUNTS] -ps [POOL SIZES] -rc [REPEAT COUNT] [-an] -fan [ANALYZE PATH]

	-ab,--about                 get info about application
	-an,--analyze               analyze algorithm
	-fan,--forceAnalyze <arg>   algorithm out path
	-ms,--matrixSizes <arg>     one dimension of square matrix
	-ps,--poolSizes <arg>       thread pool sizes
	-rc,--repeatCount <arg>     repeat count
	-tc,--threadCounts <arg>    number of threads

i.e. application default is:

	java -jar performance-1.0.jar -ms 100,150,200,250,300,350,400 -tc 2,4,6,8,10,12 -ps 2,4,6,8,10,12 -rc 20

When you want to analyze the results you should use -an command.
When you want to pre-analyzed output you should run -fan command (i.e. -fan /home/furkan/Desktop/FurkanKAMACI_performance.output)

#Where is Output?

Errors and algorithm outputs are printed into System output but application is configured to log algorithm 
specific outputs into a a .output file and .log file too. When you run the application a folder with a name "logs" will
be created and under that folder:

	FurkanKAMACI_performance.log
	FurkanKAMACI_performance.output

will be generated. .log file logs output with a verbose mode. However .output file writes algorithm spesific outputs
with a comma seperated special format. First columns is for matrix type, second one is for matrix size, 
thirth one is for pool size, fourth and last one is for thread counts.

##Author

Furkan KAMACI


