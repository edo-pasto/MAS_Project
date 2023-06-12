import jason.asSyntax.*;
import jason.environment.Environment;
import jason.environment.grid.Location;
import java.util.logging.Logger;

public class Room extends Environment {
	
	//General actions associated to the Patient
    public static final Literal takeMedicinal  = Literal.parseLiteral("take(medicinal)");
    public static final Literal sipWater  = Literal.parseLiteral("sipWater");
	public static final Literal giveMoney  = Literal.parseLiteral("giveMoney(Amount)");
    public static final Literal hasMedicinal  = Literal.parseLiteral("hasMed(patient,medicinal)");

	//Actions associated to the Nurse 1
    public static final Literal goToCabinet1 = Literal.parseLiteral("goTo(robotNurse1,cabinet)");
    public static final Literal goToPatient = Literal.parseLiteral("goTo(robotNurse1,patient)");
	public static final Literal goToStartNurse1 = Literal.parseLiteral("goTo(robotNurse1,start)");
	public static final Literal openCabinet  = Literal.parseLiteral("open(cabinet)");
    public static final Literal closeCabinet = Literal.parseLiteral("close(cabinet)");
	public static final Literal giveMedicinal  = Literal.parseLiteral("give(medicinal)");
	public static final Literal avoidObst  = Literal.parseLiteral("avoidObst(robotNurse1)");


	//Actions associated to the Nurse 2
    public static final Literal goToStart = Literal.parseLiteral("goTo(robotNurse2,start)");
    public static final Literal goToDoor = Literal.parseLiteral("goTo(robotNurse2,door)");
    public static final Literal goToCabinet2 = Literal.parseLiteral("goTo(robotNurse2,cabinet)");
	
	//Actions associated to the Robot Emergency
	public static final Literal goToPhone = Literal.parseLiteral("goTo(robotEmergency,phone)");
	public static final Literal goToStartEmerg = Literal.parseLiteral("goTo(robotEmergency,start)");
	public static final Literal callEmerg = Literal.parseLiteral("call");
	public static final Literal endCallEmerg = Literal.parseLiteral("endCall");
	public static final Literal untrustAgent = Literal.parseLiteral("untrust");
	public static final Literal endUntrustAgent = Literal.parseLiteral("untrustEnd");
	
	//Actions associated to the Payment Manager
	public static final Literal goToDoorManager = Literal.parseLiteral("goTo(paymentManager,door)");
	public static final Literal goToStartManager = Literal.parseLiteral("goTo(paymentManager,start)");
	public static final Literal goToPatientManager = Literal.parseLiteral("goTo(paymentManager,patient)");
	 
    static Logger logger = Logger.getLogger(Room.class.getName());

    RoomModel model; 

    @Override
    public void init(String[] args) {
        model = new RoomModel();
		
		// the GUI starts only if we execute the MAS environment with "on" parameter
        if (args.length == 1 && args[0].equals("on")) {
            RoomView view  = new RoomView(model);
            model.setView(view);
        }

        updatePercepts();
    }


    void updatePercepts() {
        clearPercepts("robotNurse1");
        clearPercepts("paymentManager");
        clearPercepts("robotNurse2");
        clearPercepts("patient");
		clearPercepts("robotEmergency");

        Location locNurse1 = model.getAgPos(0); //id 0 is the nurse1 

        Location locEmerg= model.getAgPos(1); //id 1 is the emergency robot

        Location locNurse2 = model.getAgPos(2); //id 2 is the nurse2
		
		Location locPayManager = model.getAgPos(3); //id 3 is the payment manager

        if (locNurse1.equals(model.locCabinet)) {
            addPercept("robotNurse1", goToCabinet1);
        }
        if (locNurse1.equals(model.locPatient)) {
            addPercept("robotNurse1", goToPatient);
        }
		 if (locNurse1.equals(model.locNurse1)) {
            addPercept("robotNurse1", goToStartNurse1);
        }

        if (locNurse2.equals(model.locDoor)) {
            addPercept("robotNurse2", goToDoor);
        }

        if (locNurse2.equals(model.locCabinet)) {
            addPercept("robotNurse2", goToCabinet2);
        }

        if (locNurse2.equals(model.locNurse2)) {
            addPercept("robotNurse2", goToStart);
        }
		if(locEmerg.equals(model.locPhone)){
			addPercept("robotEmergency", goToPhone);
		}
		if(locEmerg.equals(model.locRobotEmerg)){
			addPercept("robotEmergency", goToStartEmerg);
		}
		if(locPayManager.equals(model.locDoor)){
			addPercept("paymentManager", goToDoorManager);
		}
		if(locPayManager.equals(model.locPayManager)){
			addPercept("paymentManager", goToStartManager);
		}
		if(locPayManager.equals(model.locPatient)){
			addPercept("paymentManager", goToPatientManager);
		}

        if (model.isCabinetOpen) {
            addPercept("robotNurse1", Literal.parseLiteral("stock(medicinal,"+model.availableMeds+")"));
        }
		  if (model.hasMoney) {
            addPercept("paymentManager", Literal.parseLiteral("hasMoney(money,"+model.money+")"));
		  addPercept("paymentManager", Literal.parseLiteral("hasMoney(money,"+model.money+","+model.price+")"));
        }
        if (model.sipCount > 0) {
            addPercept("robotNurse1", hasMedicinal);
            addPercept("patient", hasMedicinal);
        }
		if (model.hasObstacle) {
			addPercept("robotNurse1", Literal.parseLiteral("hasObstacle("+model.mark+")"));
			System.out.println("ciao room true");
        }
		if (model.hasObstacle == false) {
			addPercept("robotNurse1", Literal.parseLiteral("hasObstacle("+model.mark+")"));
			System.out.println("ciao room false");
        }
    }


    @Override
    public boolean executeAction(String ag, Structure action) {
        System.out.println("["+ag+"] doing: "+action);
        boolean result = false;
        if (action.equals(openCabinet)) { 
            result = model.openCabinet();

        } else if (action.equals(closeCabinet)) {
            result = model.closeCabinet();

        } else if (action.getFunctor().equals("move")) {
            String agent = action.getTerm(0).toString();
            String loc = action.getTerm(1).toString();
            Location dest = null;

            switch(agent) {
                case "robotNurse2":
                    if (loc.equals("cabinet")) {
                        dest = model.locCabinet;
                    } else if (loc.equals("door")) {
                        dest = model.locDoor;
                    } else if (loc.equals("start")) {
                        dest = model.locNurse2;
                    }

                    try {
                        result = model.moveNurse2(dest);
                    } catch (Exception e) {
                        logger.info("Failed to move Nurse2: "+e);
                    }
                break;

                case "robotNurse1":
                    if (loc.equals("cabinet")) {
						dest = model.locCabinet;
					} else if (loc.equals("patient")) {
						dest = model.locPatient;
					}else if (loc.equals("start")) {
                        dest = model.locNurse1;
                    }                                          
		
					try {
						result = model.moveNurse1(dest);
					} catch (Exception e) {
					  logger.info("Failed to move Nurse1: "+e);
					}
                break;
				case "robotEmergency":
                    if (loc.equals("phone")) {
						dest = model.locPhone;
					} else if (loc.equals("start")) {
						dest = model.locRobotEmerg;
					}
		
					try {
						result = model.moveEmerg(dest);
					} catch (Exception e) {
					  logger.info("Failed to move robotEmergency: "+e);
					}
                break;
				case "paymentManager":
                    if (loc.equals("door")) {
						dest = model.locDoor;
					} else if (loc.equals("start")) {
						dest = model.locPayManager;
					} else if (loc.equals("patient")) {
                        dest = model.locPatient;
                    }
		
					try {
						result = model.movePayManager(dest);
					} catch (Exception e) {
					  logger.info("Failed to move paymentManager: "+e);
					}
                break;
            }
			
			
        }
		
		else if (action.equals(avoidObst)) {
            String agent = action.getTerm(0).toString();
			Location dest = new Location (model.table.x - 2, model.table.y);
			result = model.avoidObst(dest);	
		}
		else if(action.equals(callEmerg)){
			result = model.callEmerg();
			
        } else if(action.equals(endCallEmerg)){
			result = model.endCallEmerg();
			
        }else if(action.equals(untrustAgent)){
			result = model.untrustAg();
			
        } else if(action.equals(endUntrustAgent)){
			result = model.endUntrustAg();
			
        } else if (action.equals(takeMedicinal)) {
            result = model.takeMedicinal();

        } else if (action.equals(giveMedicinal)) {
            result = model.giveMedicinal();

        } else if (action.equals(sipWater)) {
            result = model.sipWater();

        } else if (action.getFunctor().equals("deliver")) {
            try {
                Thread.sleep(3000);
                result = model.addMedicinal( (int)((NumberTerm)action.getTerm(1)).solve());
            } catch (Exception e) {
                logger.info("Deliver of new medicinals is failed: "+e);
            }

        }  else if (action.getFunctor().equals("pay")) {
            try {
                Thread.sleep(2000);
                result = model.pay( (int)((NumberTerm)action.getTerm(0)).solve());
            } catch (Exception e) {
                logger.info("Payement for new medicinals is failed: "+e);
            }

        }
		 else if (action.getFunctor().equals("giveMoney")) {
            try {
                Thread.sleep(2000);
                result = model.giveMoney( (int)((NumberTerm)action.getTerm(0)).solve());
            } catch (Exception e) {
                logger.info("Give money to payment manager is failed: "+e);
            }

        } else {
            logger.info("Failed to execute action "+action);
        }

        if (result) {
            updatePercepts();
			 try {
                Thread.sleep(200);
            } catch (Exception e) {}
        }
        return result;
    }
}
