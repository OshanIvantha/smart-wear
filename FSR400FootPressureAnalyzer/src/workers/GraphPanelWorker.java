/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workers;

import java.util.List;
import javax.swing.SwingWorker;
import ui.panels.GraphPanel;

/**
 *
 * @author Ivantha
 */
public class GraphPanelWorker extends SwingWorker<Void, Void> {

    private final GraphPanel graphPanel;

    public GraphPanelWorker(GraphPanel graphPanel) {
        this.graphPanel = graphPanel;
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
        graphPanel.repaint();
    }
}
