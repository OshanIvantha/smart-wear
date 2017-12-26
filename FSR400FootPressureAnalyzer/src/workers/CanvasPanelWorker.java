/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workers;

import java.util.List;
import javax.swing.SwingWorker;
import ui.panels.CanvasPanel;

/**
 *
 * @author Ivantha
 */
public class CanvasPanelWorker extends SwingWorker<Void, Void> {

    private final CanvasPanel canvasPanel;

    public CanvasPanelWorker(CanvasPanel canvasPanel) {
        this.canvasPanel = canvasPanel;
    }

    @Override
    protected Void doInBackground() throws Exception {
        while (!this.isCancelled()) {
            publish();
        }
        return null;
    }

    @Override
    protected void process(List<Void> p) {
        canvasPanel.repaint();
    }

}
