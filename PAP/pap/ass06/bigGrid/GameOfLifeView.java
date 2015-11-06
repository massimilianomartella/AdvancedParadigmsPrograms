package pap.ass06.bigGrid;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class GameOfLifeView extends JFrame implements ActionListener,
		MouseMotionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<InputListener> listeners;
	private GameOfLifePanel setPanel;
	private JButton startButton;
	private JButton stopButton;
	private JTextField state;
	
	public Dimension dimensionPanel = new Dimension();

	public GameOfLifeView(int blockSize) {
		super("Game Of Life Viewer Test Versione 3.0");
		Toolkit tk = Toolkit.getDefaultToolkit();
		int xSize = ((int) tk.getScreenSize().getWidth());
		int ySize = ((int) tk.getScreenSize().getHeight());
		setSize(xSize,ySize-20);
		
//		setLocation(
//				(Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2,
//				(Toolkit.getDefaultToolkit().getScreenSize().height - getHeight()) / 2
//				);
		
		listeners = new ArrayList<InputListener>();

		startButton = new JButton("start");
		stopButton = new JButton("stop");
		
		stopButton.setEnabled(false);
		
		JPanel controlPanel = new JPanel();

		controlPanel.add(startButton);
		controlPanel.add(stopButton);

		setPanel = new GameOfLifePanel(new Dimension(xSize, ySize), blockSize);
//		setPanel.setSize(matrixSize);
		
//		setPanel.CannoneAliantiGosper();
		
		addMouseMotionListener(this);
		addMouseListener(this);

		JPanel infoPanel = new JPanel();
		state = new JTextField(20);
		state.setText("Ready...");
		state.setEditable(false);
		infoPanel.add(new JLabel("State"));
		infoPanel.add(state);
		JPanel cp = new JPanel();
		LayoutManager layout = new BorderLayout();
		cp.setLayout(layout);
		cp.add(BorderLayout.NORTH, controlPanel);
		
		setPanel.CannoneAliantiGosper();
		
		
		cp.add(BorderLayout.CENTER, setPanel);
		cp.add(BorderLayout.SOUTH, infoPanel);
		setContentPane(cp);

		startButton.addActionListener(this);
		stopButton.addActionListener(this);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
	}

	public void setUpdated(ArrayList<Point> result) {
		
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
					setPanel.updateImage(result);
					Toolkit.getDefaultToolkit().sync();
			}
		});
	}

	public void changeState(final String s) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				state.setText(s);
			}
		});
	}

	public void addListener(InputListener l) {
		listeners.add(l);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		setPanel.addPoint(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	public void actionPerformed(ActionEvent ev) {
		String cmd = ev.getActionCommand();
		if (cmd.equals("start")) {
			notifyStarted();
			startButton.setEnabled(false);
			stopButton.setEnabled(true);
		} else if (cmd.equals("stop")) {
			notifyStopped();
			startButton.setEnabled(true);
			stopButton.setEnabled(false);
		} 
	}

	private void notifyStarted() {
		for (InputListener l : listeners) {
			l.started(setPanel.getMatrix());
		}
	}

	private void notifyStopped() {
		for (InputListener l : listeners) {
			l.stopped();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e))
			setPanel.addPoint(e);
		if (SwingUtilities.isRightMouseButton(e))
			setPanel.removePoint(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
