/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workers;

import java.util.List;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

/**
 *
 * @author Ivantha
 */
public class CanvasPanelWorker  extends SwingWorker<Void, Void> {
    
    private final JPanel canvasPanel;

    public CanvasPanelWorker(JPanel canvasPanel) {
        this.canvasPanel = canvasPanel;
    }

    @Override
    protected Void doInBackground() throws Exception {
        while (!this.isCancelled()) {
            Thread.sleep(100);
            publish();
        }
        return null;
    }

    @Override
    protected void process(List<Void> p) {
        canvasPanel.repaint();
    }

}
