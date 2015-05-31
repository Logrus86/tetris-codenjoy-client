import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TetrisServlet extends HttpServlet {
    private TetrisSolver solver = new TetrisSolver();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String figure = req.getParameter("figure");
        int x = Integer.parseInt(req.getParameter("x"));
        int y = Integer.parseInt(req.getParameter("y"));
        String glass = req.getParameter("glass");
        String next = req.getParameter("next");
        System.out.println(String.format("Figure: %s, coordinates: (%d, %d), glass %s, next %s", figure, x, y, glass, next));
        resp.getWriter().write(solver.answer(figure, x, y, glass, next));
    }

    public static void main(String[] args) throws Exception {
        new JettyServletRunner().run();
    }

    private static class JettyServletRunner {
        /**
         * User: serhiy.zelenin
         * Date: 10/2/12
         * Time: 5:27 PM
         */
        public void run() throws Exception {
            Server server = new Server(8888);
            ServletContextHandler context = new ServletContextHandler(server, "/");
            context.addServlet(new ServletHolder(new TetrisServlet()), "/*");
            server.setHandler(context);
            server.start();
        }
    }
}
