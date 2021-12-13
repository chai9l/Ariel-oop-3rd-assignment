package api;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class DW_GraphGUI extends JFrame implements ActionListener{
    private int kRADIUS = 10;
    private int mWin_h = 1500;
    private int mWin_w = 1500;
    DW_GraphPanel panel;
    DW_GraphAlgo graphAlgo;
    DW_Graph graph;
    JMenuBar menuboya;
    JMenu graphMenu;
    JMenu graphAlgoMenu;
    JMenuItem  addNode;
    JMenuItem  getNode;
    JMenuItem  removeNode;
    JMenuItem  nodeSize;
    JMenuItem  conncet;
    JMenuItem  getEdge;
    JMenuItem  removeEdge;
    JMenuItem  edgeSize;
    JMenuItem  getMc;
    JMenuItem reset_color;
    JMenuItem getGraph;
    JMenuItem isConnected;
    JMenuItem shortestPathDist;
    JMenuItem shortestPath;
    JMenuItem center;
    JMenuItem tsp;
    JMenuItem save;
    JMenuItem load;
    DW_GraphPanel panel_switch;



    public DW_GraphGUI(DW_Graph graph){
        this.panel = new DW_GraphPanel(graph);
        graphAlgo = new DW_GraphAlgo();
        this.graphAlgo.init(graph);
        this.graph = graph;
        this.setSize(mWin_w,mWin_h);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(new Color(0xFF3D3A3A, true));

//        JPanel panel = new JPanel();
//        this.add(panel);
//        panel.setBounds(900,100,100,100);
//        JButton button = new JButton("Reset_Graph2");
//        button.addActionListener(this);

        menuboya =  new JMenuBar();


        graphMenu =  new JMenu("Graph_Methods");
        addNode = new JMenuItem("addNode");
        getNode = new JMenuItem("getNode");
        removeNode = new JMenuItem("removeNode");
        nodeSize = new JMenuItem("nodeSize");
        conncet = new JMenuItem("connect");
        getEdge = new JMenuItem("getEdge");
        removeEdge = new JMenuItem("removeEdge");
        edgeSize = new JMenuItem("edgeSize");
        getMc = new JMenuItem("getMC");
        reset_color = new JMenuItem("Reset_Colors");

        graphAlgoMenu =  new JMenu("Graph_Algo_Methods");
        getGraph = new JMenuItem("getGraph");
        isConnected = new JMenuItem("isConnected");
        shortestPathDist = new JMenuItem("shortestPathDist");
        shortestPath = new JMenuItem("shortestPath");
        center = new JMenuItem("center");
        tsp = new JMenuItem("tsp");
        save = new JMenuItem("save");
        load = new JMenuItem("load");

        graphMenu.add(reset_color);
        graphMenu.add(addNode);
        graphMenu.add(getNode);
        graphMenu.add(removeNode);
        graphMenu.add(nodeSize);
        graphMenu.add(conncet);
        graphMenu.add(getEdge);
        graphMenu.add(removeEdge);
        graphMenu.add(edgeSize);
        graphMenu.add(getMc);


        graphAlgoMenu.add(isConnected);
        graphAlgoMenu.add(shortestPathDist);
        graphAlgoMenu.add(shortestPath);
        graphAlgoMenu.add(center);
        graphAlgoMenu.add(tsp);
        graphAlgoMenu.add(save);
        graphAlgoMenu.add(load);

        reset_color.addActionListener(this);
        addNode.addActionListener(this);
        getNode.addActionListener(this);
        removeNode.addActionListener(this);
        nodeSize.addActionListener(this);
        conncet.addActionListener(this);
        getEdge.addActionListener(this);
        removeEdge.addActionListener(this);
        edgeSize.addActionListener(this);
        getMc.addActionListener(this);

        isConnected.addActionListener(this);
        shortestPathDist.addActionListener(this);
        shortestPath.addActionListener(this);
        center.addActionListener(this);
        tsp.addActionListener(this);
        load.addActionListener(this);
        save.addActionListener(this);

        menuboya.add(graphMenu);
        menuboya.add(graphAlgoMenu);
        this.add(this.panel);


        this.setJMenuBar(menuboya);
        this.setVisible(true);



    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.panel_switch != null) {
            this.panel = new DW_GraphPanel((DW_Graph) graphAlgo.getGraph());
            this.remove(panel_switch);
            this.add(panel);

        }
            if(e.getActionCommand().equals("getEdge")){
                String src = JOptionPane.showInputDialog("Enter src");
                String dest = JOptionPane.showInputDialog("Enter dest");
                int src2 = Integer.parseInt(src);
                int dest2 = Integer.parseInt(dest);
                graphAlgo.getGraph().getEdge(src2,dest2).setTag(1);
                panel_switch = new DW_GraphPanel((DW_Graph) graphAlgo.getGraph());
                this.remove(panel);
                this.add(panel_switch);
                repaint();


            }
            if (e.getActionCommand().equals("Reset_Colors")) {
                Iterator<NodeData> iter = graphAlgo.getGraph().nodeIter();
                Iterator<EdgeData> e_iter = graphAlgo.getGraph().edgeIter();
                while (iter.hasNext()) {
                    NodeData n = (myNode) iter.next();
                    graphAlgo.getGraph().getNode(n.getKey()).setTag(-1);
                }
                while (e_iter.hasNext()) {
                    EdgeData edgelord = (myEdge) e_iter.next();
                    graphAlgo.getGraph().getEdge(edgelord.getSrc(), edgelord.getDest()).setTag(0);
                }
                panel_switch = new DW_GraphPanel((DW_Graph) graphAlgo.getGraph());

                this.remove(panel);
                this.add(panel_switch);
                repaint();
            }

            if(e.getActionCommand().equals("save")){
                String new_file = JOptionPane.showInputDialog("Enter File Name (if you want in json format keep it like this : 'file_name.json')" );
                graphAlgo.save(new_file);
                JOptionPane.showMessageDialog(null,"Successfully saved the file!");
            }

            if(e.getActionCommand().equals("load")){
                String new_file = JOptionPane.showInputDialog("Enter File Path");
                graphAlgo.load(new_file);
                panel_switch = new DW_GraphPanel((DW_Graph) graphAlgo.getGraph());
                this.remove(panel);
                this.add(panel_switch);
                repaint();
            }
            if(e.getActionCommand().equals("addNode")) {
                String idadd = JOptionPane.showInputDialog("enter ID");
                String x = JOptionPane.showInputDialog("enter x");
                String y = JOptionPane.showInputDialog("enter y");
                double _x = graph.offset_x + Double.parseDouble(x)/ graph.factor_x;
                double _y = graph.offset_y + Double.parseDouble(y)/ graph.factor_y;
                int id_a = Integer.parseInt(idadd);
                myNode n = new myNode(_x, _y, 0, id_a);
                graphAlgo.getGraph().addNode(n);
                panel_switch = new DW_GraphPanel((DW_Graph) graphAlgo.getGraph());
                this.remove(panel);
                this.add(panel_switch);
                repaint();
            }

            if(e.getActionCommand().equals("removeNode")) {
                String id_re = JOptionPane.showInputDialog("enter Id");
                int id_r = Integer.parseInt(id_re);
                graphAlgo.getGraph().removeNode(id_r);
                panel_switch = new DW_GraphPanel((DW_Graph) graphAlgo.getGraph());
                this.remove(panel);
                this.add(panel_switch);
                repaint();
            }
            if(e.getActionCommand().equals("getNode")) {
                String idget = JOptionPane.showInputDialog("enter id");
                int id_g = Integer.parseInt(idget);
                graphAlgo.getGraph().getNode(id_g).setTag(2);
                panel_switch = new DW_GraphPanel((DW_Graph) graphAlgo.getGraph());
                this.remove(panel);
                this.add(panel_switch);
                repaint();
            }

            if(e.getActionCommand().equals("removeEdge")) {
                String edge_src = JOptionPane.showInputDialog("enter src");
                String edge_dest = JOptionPane.showInputDialog("enter dest");
                int src = Integer.parseInt(edge_src);
                int dest = Integer.parseInt(edge_dest);
                graphAlgo.getGraph().removeEdge(src, dest);
                panel_switch = new DW_GraphPanel((DW_Graph) graphAlgo.getGraph());
                this.remove(panel);
                this.add(panel_switch);
                repaint();
            }
            if(e.getActionCommand().equals("connect")){
                String node_src = JOptionPane.showInputDialog("Enter src node");
                String node_dest = JOptionPane.showInputDialog("Enter dest node");
                String edge_weight =  JOptionPane.showInputDialog("Enter edge weight");
                int src1 = Integer.parseInt(node_src);
                int dest1 = Integer.parseInt(node_dest);
                int edge_w = Integer.parseInt(edge_weight);
                graphAlgo.getGraph().connect(src1,dest1,edge_w);
                panel_switch = new DW_GraphPanel((DW_Graph) graphAlgo.getGraph());
                this.remove(panel);
                this.add(panel_switch);
                repaint();
            }
            if(e.getActionCommand().equals("nodeSize")) {
                JOptionPane.showMessageDialog(null,"Amount of nodes in the graph is: " + graphAlgo.getGraph().nodeSize());
            }
            if(e.getActionCommand().equals("edgeSize")) {
                JOptionPane.showMessageDialog(null,"Amount of edges in the graph is: " + graphAlgo.getGraph().edgeSize());

            }
            if (e.getActionCommand().equals("getMC")) {
                JOptionPane.showMessageDialog(null,"Amount of changes in the graph is: " + graphAlgo.getGraph().getMC());
            }
            if(e.getActionCommand().equals("isConnected")) {
                Iterator<NodeData> iter = graphAlgo.getGraph().nodeIter();
                List<Integer> list = new ArrayList<>();
                while(iter.hasNext()) {
                    NodeData n = (myNode) iter.next();
                    if(n.getTag() == 2) {
                        list.add(n.getKey());
                    }
                }
                boolean flag = graphAlgo.isConnected();
                if(flag) {
                    JOptionPane.showMessageDialog(null, "is Connected");
                }else
                    JOptionPane.showMessageDialog(null, "Not Connected");


                Iterator<Integer> iter2 = list.iterator();
                while(iter2.hasNext()) {
                    int i = iter2.next();
                    graphAlgo.getGraph().getNode(i).setTag(2);

                }
                panel_switch = new DW_GraphPanel((DW_Graph) graphAlgo.getGraph());
                this.remove(panel);
                this.add(panel_switch);
                repaint();
            }

            if(e.getActionCommand().equals(("center"))) {
                NodeData n = (myNode) graphAlgo.center();
                int key = n.getKey();
                Iterator<NodeData> iter = graphAlgo.getGraph().nodeIter();
                while (iter.hasNext()) {
                    NodeData n2 = (myNode) iter.next();
                    graphAlgo.getGraph().getNode(n2.getKey()).setTag(-1);

                }
                JOptionPane.showMessageDialog(null, "Center Node is: " + n.getKey());
                graphAlgo.getGraph().getNode(key).setTag(2);
                panel_switch = new DW_GraphPanel((DW_Graph) graphAlgo.getGraph());
                this.remove(panel);
                this.add(panel_switch);
                repaint();
            }
            if(e.getActionCommand().equals("shortestPathDist")){
                String node_src = JOptionPane.showInputDialog("Enter src node");
                String node_dest = JOptionPane.showInputDialog("Enter dest node");
                int src = Integer.parseInt(node_src);
                int dest = Integer.parseInt(node_dest);
                double dist = graphAlgo.shortestPathDist(src,dest);
                JOptionPane.showMessageDialog(null,"The distance is : " + dist);

            }
            if(e.getActionCommand().equals("shortestPath")){
                String node_src = JOptionPane.showInputDialog("Enter src node");
                String node_dest = JOptionPane.showInputDialog("Enter dest node");
                int src = Integer.parseInt(node_src);
                int dest = Integer.parseInt(node_dest);

                List<NodeData> list = graphAlgo.shortestPath(src,dest);
                Queue<NodeData> queue = new LinkedList<>(list);
                while(!queue.isEmpty()) {
                    NodeData poll = queue.poll();
                    NodeData peek = queue.peek();
                    if (peek == null) {
                        graphAlgo.getGraph().getNode(poll.getKey()).setTag(2);
                        break;
                    }
                    graphAlgo.getGraph().getEdge(poll.getKey(), peek.getKey()).setTag(1);
                    graphAlgo.getGraph().getNode(poll.getKey()).setTag(2);
                }
                panel_switch = new DW_GraphPanel((DW_Graph) graphAlgo.getGraph());
                this.remove(panel);
                this.add(panel_switch);
                repaint();
            }
            if(e.getActionCommand().equals("tsp")){
                String arr_size = JOptionPane.showInputDialog("Please enter size of the list of nodes");
                int arr_size1 = Integer.parseInt(arr_size);
                List<NodeData> list = new ArrayList<NodeData>();
                for(int i = 0; i< arr_size1 ;i++){
                    String node_id = JOptionPane.showInputDialog("Enter node id");
                    int node_id1 = Integer.parseInt(node_id);
                    list.add(graphAlgo.getGraph().getNode(node_id1));
                }
                List<NodeData> tsp_list = graphAlgo.tsp(list);
                Iterator<NodeData> iter = tsp_list.iterator();
                String ans = "";
                Queue<NodeData> queue = new LinkedList<>(tsp_list);
                while(!queue.isEmpty()) {
                    NodeData poll = queue.poll();
                    NodeData peek = queue.peek();
                    if (peek == null) {
                        graphAlgo.getGraph().getNode(poll.getKey()).setTag(2);
                        break;
                    }
                    graphAlgo.getGraph().getEdge(poll.getKey(), peek.getKey()).setTag(1);
                    graphAlgo.getGraph().getNode(poll.getKey()).setTag(2);
                }
                while(iter.hasNext()){
                    NodeData node = (myNode) iter.next();
                    ans = ans + node.getKey() + ",";
                }
                JOptionPane.showMessageDialog(null,"tsp list: " + ans);
                panel_switch = new DW_GraphPanel((DW_Graph) graphAlgo.getGraph());
                this.remove(panel);
                this.add(panel_switch);
                repaint();
            }


        }


}
