import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Notepad extends JFrame implements ActionListener
{
    JMenuBar menuBar = new JMenuBar();
    JTextArea area = new JTextArea();
    JScrollPane pane = new JScrollPane(area);
    JFileChooser choose = new JFileChooser();
    JFileChooser saveas = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Text File", "txt");

    JMenu File = new JMenu("File");
    JMenu Edit = new JMenu("Edit");

    JMenuItem NEW = new JMenuItem("New");
    JMenuItem OPEN = new JMenuItem("Open");
    JMenuItem SAVE = new JMenuItem("Save");
    JMenuItem EXIT = new JMenuItem("Exit");

    JMenuItem Cut = new JMenuItem("Cut");
    JMenuItem Copy = new JMenuItem("Copy");
    JMenuItem Paste = new JMenuItem("Paste");
    JMenuItem Delete = new JMenuItem("Delete");

    Notepad()
    {
        setLayout(new BorderLayout());

        // Set up the menu bar
        menuBar.setBackground(Color.white);
        menuBar.setFont(new Font("Arial", Font.ITALIC, 12));
        setJMenuBar(menuBar);

        // Set up the File menu
        File.add(NEW);
        File.add(OPEN);
        File.add(SAVE);
        File.add(EXIT);

        NEW.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        OPEN.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        SAVE.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        EXIT.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));

        // Set up the Edit menu
        Edit.add(Cut);
        Edit.add(Copy);
        Edit.add(Paste);
        Edit.add(Delete);

        Cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        Copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        Paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
        Delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, InputEvent.CTRL_DOWN_MASK));

        // Add components to the menu bar
        menuBar.add(File);
        menuBar.add(Edit);

        // Set up the JTextArea
        area.setWrapStyleWord(true);
        area.setLineWrap(true);

        // Add action listeners
        NEW.addActionListener(this);
        OPEN.addActionListener(this);
        SAVE.addActionListener(this);
        EXIT.addActionListener(this);
        Cut.addActionListener(this);
        Copy.addActionListener(this);
        Paste.addActionListener(this);
        Delete.addActionListener(this);

        // Add the JScrollPane containing JTextArea to the JFrame
        add(pane, BorderLayout.CENTER);

        // Set JFrame visibility after all components are added
        setVisible(true);
        setTitle("Notepad");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String command = e.getActionCommand();
        if (command.equals("New"))
        {
            area.setText("");
        }
        else if (command.equals("Open"))
        {
            choose.setAcceptAllFileFilterUsed(false);
            choose.addChoosableFileFilter(filter);
            int action = choose.showOpenDialog(this);
            if (action == JFileChooser.APPROVE_OPTION) {
                File file = choose.getSelectedFile();
                try (BufferedReader br = new BufferedReader(new FileReader(file)))
                {
                    area.read(br, null);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        else if (command.equals("Save"))
        {
            saveas.setAcceptAllFileFilterUsed(false);
            saveas.addChoosableFileFilter(filter);
            saveas.setApproveButtonText("Save");
            int saveAction = saveas.showSaveDialog(this);
            if (saveAction == JFileChooser.APPROVE_OPTION)
            {
                File file = saveas.getSelectedFile();
                if (!file.getName().endsWith(".txt"))
                {
                    file = new File(file.getAbsolutePath() + ".txt");
                }
                try (BufferedWriter output = new BufferedWriter(new FileWriter(file)))
                {
                    area.write(output);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
        else if (command.equals("Exit"))
        {
            System.exit(0);
        }
    }

    public static void main(String[] args)
    {
        new Notepad();
    }
}