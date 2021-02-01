import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;



public class Main extends JFrame {

    private JPanel mainPanel;
    private JCanvasPanel canvas;
    private final int windowToleranceX;
    private final int windowToleranceY;
    private JPanel buttonPanel;

    private  JButton stopButton;
    private JButton refreshButton;
    private  JButton playButton;
    private JButton screenshotButton;
    private JButton rainButton;
    private JButton rebornButton;

    private JComboBox windCombo;
    private JComboBox forestTypeCombo;
    static public JComboBox seasonCombo;
    private JComboBox mouseChangerCombo;
    private JComboBox initialCombo;

    private JTextField probabilityField;
    private JTextField humidityField;

    static DataManager dm;
    Utility util;

    public Main(String title) throws IOException {

        super(title);
        dm = new DataManager();
        //img 600x480

        util = new Utility(dm);
        windowToleranceX=8; //Metoda prob i bledow- probowalem wskazac pixel 0,0 i taka wartosc mi wyskakiwala
        windowToleranceY=31;


        String[] startingText ={"None","N","W","S","E"};
        String[] seasonText ={"None","Summer","Winter","Spring","Autumn"};
        String[] forestTypeText ={"None","Coniferous","Deciduous"};
        String[] mouseType={"Tree","Water","Burned Tree","Dead","Firefighter"};
        String[] initialText={"My choice", "Random"};
        initialCombo=new JComboBox(initialText);
        windCombo =new JComboBox(startingText);
        seasonCombo=new JComboBox(seasonText);
        forestTypeCombo=new JComboBox(forestTypeText);
        mouseChangerCombo=new JComboBox(mouseType);

        initialCombo.setBorder(new TitledBorder("Initial Fire"));
        mouseChangerCombo.setBorder(new TitledBorder("State for mouse"));
        windCombo.setBorder(new TitledBorder("Select Wind"));
        seasonCombo.setBorder(new TitledBorder("Select Season"));
        forestTypeCombo.setBorder(new TitledBorder("Select Forest Type"));

        humidityField=new JTextField("10");
        probabilityField=new JTextField("50");

        playButton =new JButton("Start Fire");
        stopButton=new JButton("Stop Algorithm");
        rainButton =new JButton("Rain");
        rebornButton=new JButton("Reborn Forest");
        probabilityField.setBorder(new TitledBorder("Probability"));
        humidityField.setBorder(new TitledBorder("Input Humidity"));
        refreshButton=new JButton("REFRESH");
        screenshotButton=new JButton("Screenshot");

        //Add Buttons

        canvas = new JCanvasPanel(dm);

        buttonPanel = new JPanel();

        buttonPanel.add(windCombo);
        buttonPanel.add(seasonCombo);
        buttonPanel.add(forestTypeCombo);
        buttonPanel.add(mouseChangerCombo);
        buttonPanel.add(initialCombo);
        buttonPanel.add(probabilityField);
        buttonPanel.add(humidityField);
        buttonPanel.add(refreshButton);
        buttonPanel.add(playButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(rainButton);
        buttonPanel.add(rebornButton);
        buttonPanel.add(screenshotButton);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        buttonPanel.setLayout(new GridLayout(14, 1));

        mainPanel.add(BorderLayout.CENTER, canvas);
        mainPanel.add(BorderLayout.EAST, buttonPanel);

        //Else

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);

        this.setSize(new Dimension(700, 600));
        this.setLocationRelativeTo(null);


        //Animation
        AtomicBoolean fireState=new AtomicBoolean(false);
        AtomicBoolean rainState=new AtomicBoolean(false);
        AtomicBoolean rebornState=new AtomicBoolean(false);
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
           if(fireState.get()) {
               util.initialConditions(initialCombo.getSelectedItem().toString());
               util.fireSpread(dm,dm.getStateCells(),Integer.parseInt(probabilityField.getText()),Integer.parseInt(humidityField.getText()),
                       windCombo.getSelectedItem().toString(),seasonCombo.getSelectedItem().toString(),forestTypeCombo.getSelectedItem().toString());
              util.checkStatus();

           }
            else if(rainState.get())
               util.rain(dm, dm.getStateCells());
            else if(rebornState.get())
               util.rebornForest(dm, dm.getStateCells());


        }, 0, 500, TimeUnit.MILLISECONDS);

        //Action Listener

        playButton.addActionListener(e->{
            fireState.set(true);
        });

        rainButton.addActionListener(e->{
            rainState.set(true);
        });

        rebornButton.addActionListener(e->{
            rebornState.set(true);
        });

        stopButton.addActionListener(e->{
            fireState.set(false);
            rainState.set(false);
            rebornState.set(false);
        });

        refreshButton.addActionListener(e -> {
            util.reset();
        });

        screenshotButton.addActionListener(e->{
            Tools.takeSnapShot(canvas);
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x=e.getX()-windowToleranceX;
                int y=e.getY()-windowToleranceY;
                util.MouseChangeState(x,y,mouseChangerCombo.getSelectedItem().toString());
                //canvas.repaint();
            }
        });
        util.Binarization(dm,65, dm.getStateCells());
    }

    public static void main(String[] args) throws IOException {

        Main mw = new Main("Forest Fire Model");
        mw.setVisible(true);



    }
}


