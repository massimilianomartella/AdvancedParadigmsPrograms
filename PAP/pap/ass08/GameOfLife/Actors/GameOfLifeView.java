package pap.ass08.GameOfLife.Actors;

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

/**
 * GameOfLifeView rappresenta la View del progetto. Implementa: - ActionListener
 * (ascoltatori) - MouseMotionListener (tasto premuto del mouse, disegno sulla
 * griglia) - MouseListener (alla pressione del tasto, disegno un punto sulla
 * griglia)
 * 
 * @author Martella Massimiliano
 *
 */
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

	public GameOfLifeView(Dimension windowsSize, Dimension matrixSize,
			int blockSize) {
		super("Game Of Life Viewer With Actors");
		setSize(windowsSize);

		// Centrare la finestra al centro dello schermo
		setLocation(
				(Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getHeight()) / 2);

		listeners = new ArrayList<InputListener>();

		startButton = new JButton("start");
		stopButton = new JButton("stop");

		// All'avvio il tasto stop è disabilitato
		stopButton.setEnabled(false);

		JPanel controlPanel = new JPanel();

		controlPanel.add(startButton);
		controlPanel.add(stopButton);

		setPanel = new GameOfLifePanel(matrixSize, blockSize);
		setPanel.setSize(matrixSize);

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

		// Aggiungo nella grigia il Cannone di Alinti di Gosper come test
		setPanel.CannoneAliantiGosper();

		cp.add(BorderLayout.CENTER, setPanel);
		cp.add(BorderLayout.SOUTH, infoPanel);
		setContentPane(cp);

		startButton.addActionListener(this);
		stopButton.addActionListener(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
	}

	/**
	 * Tramite il metodo invokeLater viene passato al EventDispatchThread il
	 * compito di aggiornare la griglia a video
	 * 
	 * @param result
	 *            : lista di punti da aggiornare
	 */
	public void setUpdated(final ArrayList<Point> result) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setPanel.updateMatrix(result);
				
			}
		});
	}

	/**
	 * Tramite il metodo invokeLater viene passato al EventDispatchThread il
	 * compito di aggiornare la text a video
	 * 
	 * @param s : stringa da stampare nella TextBox della view
	 */
	public void changeState(final String s) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				state.setText(s);
			}
		});
		
	}

	/**
	 * Imposta gli ascoltatori
	 * @param l
	 */
	public void addListener(InputListener l) {
		listeners.add(l);
	}

	/**
	 * Tutte le volte che il mouse viene spostato mentre è premuto il tasto sinisto, viene disegnato un punto sulla griglia
	 */
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

	/**
	 * Notifico al controller, passandogli la griglia, di partire con l'algoritmo
	 */
	private void notifyStarted() {
		for (InputListener l : listeners) {
			l.started(setPanel.getMatrix());
		}
	}

	/**
	 * Notifico al controller di fermarsi
	 */
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

	/**
	 * Al rilascio del tasto sinistro del mouse disegno un punto sulla griglia
	 */
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
