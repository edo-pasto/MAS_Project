# Domestic Health Assistant
## Multi Agent System's project, A.Y. 2022/2023
#### Edoardo Pastorino, 5169595

--------
## Introduction
This project, using the definition of a MAS, refers to an hypothetical situation in which a sick patient is stuck in the bed of his/her room for some illness. This patient need to be cured ingesting some medicines, like syrup, antibiotic or pills. For this reason, he/she needs help from some robot nurse agents to complete this process. He/She could has also the need to call the 911 emergency number if his/her health should suddenly worsen, asking to the appropriate SOS agent to call the specific telephone number, thanks to a phone situated inside the room. Since the medicines inside the cabinet sooner or later could finish, another agent is responsible to order and refill the cabinet with the new delivered medicinal. At the same time the patient has to pay the ambulance that performs the deliver service. The sick person is able to make the payment thanks to the help of a payment manager agent. All these processes are implemented following the syntax, based on the BNF form (Backus-Naur form), of an AgentSpeak(L) language called Jason, a Java-based interpreter for an extended version of AgentSpeak.
 
--------
## Directory Info
This is the directory of th Multi Agent System's project. The main directory "MASProject" contains all the files needed for making work the project:
- the three java files, used to create the graphical environment.
- the six asl files, containing the Jason code of each agent. More precisely they are situated inside src/asl/ directory.

## Basic Instructions
-  Install the JDK (version greater or equal 1.8v).
-  Install Jason language (https://sourceforge.net/projects/jason/files/jason/)
-  Move inside the jason-3.1/jedit directory and run, on the prompt, the command:
    ```
    java -jar jedit.jar
    ```
    and a Jason IDE will open soon.
- Inside the IDE window click on "File" --> "Open" and choose the file named "domesticHealtAssistant.mas2j"
- Click on the green play button on the lower-right part of the window to start the project simulation.
