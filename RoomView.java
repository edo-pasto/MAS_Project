import jason.environment.grid.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


public class RoomView extends GridWorldView {

    RoomModel roomModel;

    public RoomView(RoomModel model) {
        super(model, "Domestic Health Assistant", 1200);
        roomModel = model;
        defaultFont = new Font("Roboto", Font.BOLD, 10);
        setVisible(true);
		setBackground(new Color(240, 231, 196));
		
        repaint();
    }
	
   //Drawing the objects (and static agent patient) on the grid
    @Override
    public void draw(Graphics g, int x, int y, int object) {
        Location locAgent = roomModel.getAgPos(0);

        super.drawAgent(g, x, y, Color.lightGray, -1);
        switch (object) {
        case RoomModel.CABINET:
            if (locAgent.equals(roomModel.locCabinet)) {
                super.drawAgent(g, x, y, Color.yellow, -1);
            }
            g.setColor(Color.black);
            drawString(g, x, y, defaultFont, "CABINET ("+roomModel.availableMeds+" Meds)");
            break;
        case RoomModel.DOOR:
            super.drawAgent(g, x, y, Color.lightGray, -1);
            g.setColor(Color.BLUE);
            drawString(g, x, y, new Font("Arial", Font.BOLD, 16), "DOOR");
            break;
        case RoomModel.PATIENT:
            if (locAgent.equals(roomModel.locPatient)) {
                super.drawAgent(g, x, y, Color.yellow, -1);
            }
            String o = "Patient";
            if (roomModel.sipCount > 0) {
                o +=  " ("+roomModel.sipCount+")";
            }
            g.setColor(Color.black);
            drawString(g, x, y, defaultFont, o);
            break;
		case RoomModel.PHONE:
            super.drawAgent(g, x, y, Color.lightGray, -1);
            g.setColor(Color.RED);
            drawString(g, x, y, new Font("Roboto", Font.BOLD, 16), "PHONE");
            break;
        }
		super.drawObstacle(g, 7, 7);
		//super.drawObstacle(g, 5, 1);
        repaint();
    }

	//Drawing the dynamic agents
    @Override
    public void drawAgent(Graphics g, int x, int y, Color color, int id) {
        Location locNurse1 = roomModel.getAgPos(0);

        if (!locNurse1.equals(roomModel.locPatient) && !locNurse1.equals(roomModel.locCabinet)) {
            color = Color.orange;
            if (roomModel.isGiveMed) color = Color.yellow;
            super.drawAgent(g, x, y, color, -1);
            g.setColor(Color.black);
            drawString(g, x, y, defaultFont, "Nurse1");
        }

        if (id == 1) { 
           color = Color.red;
            super.drawAgent(g, x, y, color, -1);
            g.setColor(Color.black);
			if(roomModel.call.equals(" ") && roomModel.untrust.equals(" ")){
				drawString(g, x, y, defaultFont,"SOS");
			}else if(roomModel.call.equals("Calling 911")){
				drawString(g, x, y, defaultFont, roomModel.call); 
			}else if(roomModel.untrust.equals("AG untrusted")){
				drawString(g, x, y, defaultFont, roomModel.untrust);
			}
        }
	
        if (id == 2) {
            color = Color.orange;
            super.drawAgent(g, x, y, color, -1);
            g.setColor(Color.black);
            drawString(g, x, y, defaultFont, "Nurse2");
        }
		if (id == 3) {
			Location locPayManager = roomModel.getAgPos(id);
			color = Color.green;
			super.drawAgent(g, x, y, color, -1);
			g.setColor(Color.black);
			drawString(g, x, y, defaultFont, "PayManager (" +roomModel.money+" $)");

        }
    }
}
