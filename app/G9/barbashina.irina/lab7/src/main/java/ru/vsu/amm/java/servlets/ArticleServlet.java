    package ru.vsu.amm.java.servlets;

    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.HttpServlet;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import ru.vsu.amm.java.entities.Article;
    import ru.vsu.amm.java.service.ArticleService;
    import ru.vsu.amm.java.service.impl.ArticleServiceImpl;

    import java.io.IOException;

    public class ArticleServlet extends HttpServlet {
        private final ArticleService articleService = new ArticleServiceImpl();

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws IOException, ServletException {

            String action = req.getParameter("action");

            if ("new".equals(action)) {
                req.getRequestDispatcher("/article-form.jsp").forward(req, resp);
                return;
            }

            if ("edit".equals(action)) {
                req.setAttribute("article", articleService.getArticleById(
                        Long.parseLong(req.getParameter("id"))));
                req.getRequestDispatcher("/article-form.jsp").forward(req, resp);
                return;
            }

            req.setAttribute("articles", articleService.getAllArticles());
            req.getRequestDispatcher("/article-list.jsp").forward(req, resp);

        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws IOException {

            String action = req.getParameter("action");
            Long id = req.getParameter("id") != null ?
                    Long.parseLong(req.getParameter("id")) : null;

            Article article = new Article();
            article.setId(id);
            article.setTitle(req.getParameter("title"));
            article.setСontent(req.getParameter("content"));

            if("insert".equals(action)) {
                articleService.createArticle(article);
            } else {
                articleService.updateArticle(article);
            }

            resp.sendRedirect("articles");
        }
    }
