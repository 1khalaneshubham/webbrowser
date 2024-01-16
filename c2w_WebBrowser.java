
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

import java.util.ArrayList;


public class c2w_WebBrowser extends Application {

    public static void main(String[] c2w_args) {
        launch(c2w_args);
    }
   
    
    private ArrayList<c2w_BrowserWindow> c2w_openWindows;  
    private Rectangle2D c2w_screenRect;                
    private double c2w_locationX, c2w_locationY;           
    private double c2w_windowWidth, c2w_windowHeight;      
    private int c2w_untitledCount;                    
    
    
    public void start(Stage stage) {
        
        c2w_openWindows = new ArrayList<c2w_BrowserWindow>();  
        c2w_screenRect = Screen.getPrimary().getVisualBounds();
        
          
        c2w_locationX = c2w_screenRect.getMinX() + 30;
        c2w_locationY = c2w_screenRect.getMinY() + 20;
        
        c2w_windowHeight = c2w_screenRect.getHeight() - 160;
        c2w_windowWidth = c2w_screenRect.getWidth() - 130;
        if (c2w_windowWidth > c2w_windowHeight*1.6)
            c2w_windowWidth = c2w_windowHeight*1.6;
        
           // Open the first window, showing the front page of this textbook.
        c2w_newBrowserWindow("https://www.core2web.in/privacypolicy.html");

    } // end start()
    
    /**
     * Get the list of currently open windows.  The browser windows use this
     * list to construct their Window menus.
     * A package-private method that is meant for use only in c2w_BrowserWindow.java.
     */
    ArrayList<c2w_BrowserWindow> getOpenWindowList() {
        return c2w_openWindows;
    }
    
    /**
     * Get the number of window titles of the form "Untitled XX" that have been
     * used.  A new window that is opened with a null c2w_URL gets a title of
     * that form.  This method is also used in c2w_BrowserWindow to provide a
     * title for any web page that does not itself provide a title for the page.
     * A package-private method that is meant for use only in c2w_BrowserWindow.java.
     */
    int c2w_getNextUntitledCount() {
        return ++c2w_untitledCount;
    }
    
    /**
     * Open a new browser window.  If c2w_url is non-null, the window will load that c2w_URL.
     * A package-private method that is meant for use only in c2w_BrowserWindow.java.
     * This method manages the locations for newly opened windows.  After a window
     * opens, the next window will be offset by 30 pixels horizontally and by 20
     * pixels vertically from the location of this window; but if that makes the
     * window extend outside c2w_screenRect, the horizontal or vertical position will
     * be reset to its minimal value.
     */
    void c2w_newBrowserWindow(String c2w_url) {
        c2w_BrowserWindow window = new c2w_BrowserWindow(this,c2w_url);
        c2w_openWindows.add(window);   // Add new window to open window list.
        window.setOnHidden( e -> {
                // Called when the window has closed.  Remove the window
                // from the list of open windows.
            c2w_openWindows.remove( window );
            System.out.println("Number of open windows is " + c2w_openWindows.size());
            if (c2w_openWindows.size() == 0) {
                // Program ends automatically when all windows have been closed.
                System.out.println("Program will end because all windows have been closed");
            }
        });
        if (c2w_url == null) {
            window.setTitle("c2w_Untitled " + c2w_getNextUntitledCount());
        }
        window.setX(c2w_locationX);         // set location and size of the window
        window.setY(c2w_locationY);
        window.setWidth(c2w_windowWidth);
        window.setHeight(c2w_windowHeight);
        window.show();
        c2w_locationX += 30;    // set up location of NEXT window
        c2w_locationY += 20;
        if (c2w_locationX + c2w_windowWidth + 10 > c2w_screenRect.getMaxX()) {
                // Window would extend past the right edge of the screen,
                // so reset c2w_locationX to its original value.
            c2w_locationX = c2w_screenRect.getMinX() + 30;
        }
        if (c2w_locationY + c2w_windowHeight + 10 > c2w_screenRect.getMaxY()) {
                // Window would extend past the bottom edge of the screen,
                // so reset c2w_locationY to its original value.
            c2w_locationY = c2w_screenRect.getMinY() + 20;
        }
    }
    
    
} // end WebBrowser