package org.cleveroad;

import org.cleveroad.service.UserService;
import org.cleveroad.view.ViewUsers;

import javax.swing.*;

public class App 
{
    private static final UserService userService = new UserService();

    public static void main( String[] args )
    {
        //init() function gets data from https://randomuser.me/documentation
        //and places it to local DB
        //By passing number to this function you specify amount of users you need

        //userService.init(100);
        SwingUtilities.invokeLater(App::createGUI);
    }

    private static void createGUI(){
        ViewUsers gui = new ViewUsers(userService.findAll());
        JPanel root = gui.getRootPanel();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(root);
        frame.pack();
        frame.setVisible(true);
    }
}
