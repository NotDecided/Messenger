package DisplayOperationsIncluded;

import java.awt.*;

import javax.swing.*;
import javax.swing.JSpinner.DateEditor;
import javax.swing.event.*;

import java.util.Calendar;
import java.util.Date;



public class JavaLessonTwentyeight extends JFrame
{
	
	JLabel nameLabel,ageLabel,streetLabel,sexLabel,stateLabel,dateLabel, optionLabel,aboutLabel;
	JTextField streetText,nameText;
	JComboBox stateList;//list with drop down menu
	JSpinner  dateSpinner;
	JSlider ageSlider;
	JRadioButton maleRadio , femaleRadio;
	ButtonGroup sexGroup;
	JCheckBox morningCheck , afterNoonCheck , eveningCheck;
	JTextArea aboutYou;
	
	
	
	public static void main(String[]args)
	{
		new JavaLessonTwentyeight();
	}
	
	public JavaLessonTwentyeight()
	{
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setTitle("Random layout");
		
		JPanel thePanel = new JPanel();
		
		thePanel.setLayout(new GridBagLayout());
		
		nameLabel = new JLabel("Name:");
		addComponent(thePanel,nameLabel,0,0,1,1,GridBagConstraints.EAST,GridBagConstraints.NONE);
		
		nameText = new JTextField(30);
		addComponent(thePanel,nameText,1,0,2,1,GridBagConstraints.WEST,GridBagConstraints.NONE);
		
		streetLabel = new JLabel("Street:");
		addComponent(thePanel,streetLabel,0,1,1,1,GridBagConstraints.EAST,GridBagConstraints.NONE);
		
		streetText = new JTextField(30);
		addComponent(thePanel,streetText,1,1,2,1,GridBagConstraints.WEST,GridBagConstraints.NONE);
		
		
		Box sexBox = Box.createVerticalBox();
		maleRadio = new JRadioButton("Male");
		femaleRadio = new JRadioButton("Female");
		
		sexGroup = new ButtonGroup();//defining a group for radio buttons in order to allow on mode only for one button at the same time
		sexGroup.add(maleRadio);
		sexGroup.add(femaleRadio);
		sexBox.add(maleRadio);
		sexBox.add(femaleRadio);
		sexBox.setBorder(BorderFactory.createTitledBorder("SEX"));
		addComponent(thePanel,sexBox,2,0,2,1,GridBagConstraints.WEST,GridBagConstraints.NONE);
		
		JPanel statePanel = new JPanel();
		statePanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,0));
		
		stateLabel = new JLabel("State");
		statePanel.add(stateLabel);
		
		String[] states = {"CA","NY","TX","WA","PA","FL"};
		stateList = new JComboBox(states);
		statePanel.add(stateList);
		
		dateLabel = new JLabel("Appointment date");
		statePanel.add(dateLabel);
		
		Date todaysDate = new Date();
		
		dateSpinner = new JSpinner(new SpinnerDateModel(todaysDate,null,null,Calendar.DAY_OF_MONTH));
		
		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner,"dd/MM/yy");
		dateSpinner.setEditor(dateEditor);
		
		statePanel.add(dateSpinner);
		
		ageLabel = new JLabel("Age: 50");
		statePanel.add(ageLabel);
		
		ageSlider = new JSlider(1,99,50);
		ListenerForSliders sliderListener = new ListenerForSliders();
		ageSlider.addChangeListener(sliderListener);
		statePanel.add(ageSlider);
		
		
		addComponent(thePanel,sexBox,1,2,5,1,GridBagConstraints.WEST,GridBagConstraints.NONE);
		
		Box optionBox = Box.createVerticalBox();
		morningCheck = new JCheckBox("Morning");
		afterNoonCheck = new JCheckBox("Afternoon");
		eveningCheck = new JCheckBox("Evening");
		optionBox.add(morningCheck);
		optionBox.add(afterNoonCheck);
		optionBox.add(eveningCheck);
		optionBox.setBorder(BorderFactory.createTitledBorder("Time of day"));
		addComponent(thePanel,optionBox,0,3,2,1,GridBagConstraints.NORTHWEST,GridBagConstraints.NONE);
		
		aboutYou = new JTextArea(6,40);
		aboutYou.setText("Tell us about you");
		aboutYou.setWrapStyleWord(true);
		aboutYou.setLineWrap(true);
		
		JScrollPane scrollBar1 = new JScrollPane(aboutYou,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		addComponent(thePanel,nameLabel,0,0,1,1,GridBagConstraints.EAST,GridBagConstraints.NONE);
		addComponent(thePanel,statePanel,3,2,5,1,GridBagConstraints.EAST,GridBagConstraints.NONE);
		addComponent(thePanel,scrollBar1,2,3,3,1,GridBagConstraints.EAST,GridBagConstraints.NONE);
		
		this.add(thePanel);
		
		
		this.pack();//resizes the window to fit all components on the screen
		this.setVisible(true);
		this.setResizable(false);
		
	}
	
	private class ListenerForSliders implements ChangeListener
	{

		@Override
		public void stateChanged(ChangeEvent e) {
		
			if(e.getSource() == ageSlider)
			{
			ageLabel.setText("Age : " + ageSlider.getValue());
			}
			
		}
		
	}
	
	private void addComponent(JPanel thePanel , JComponent theComponent,int Xpos,int Ypos,int componentWidth , int componentHeight, int place , int stretch )
	{
		
		GridBagConstraints gridConstraints = new GridBagConstraints();
		
		gridConstraints.gridx = Xpos;
		gridConstraints.gridy = Ypos;
		gridConstraints.gridwidth = componentWidth;
		gridConstraints.gridheight = componentHeight;
		gridConstraints.weightx = 100;
		gridConstraints.weighty = 100;
		gridConstraints.insets = new Insets(5,5,5,5);
		gridConstraints.anchor = place;
		gridConstraints.fill = stretch;
		
		
		thePanel.add(theComponent,gridConstraints);//don't forget to add the grid constraints to the panel
	}
}