package com.example.devSync.webService;

import com.example.devSync.bean.Tag;
import com.example.devSync.service.TagService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/tags")
public class TagWebService extends HttpServlet {

    private final TagService tagService = new TagService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "getAll":
                List<Tag> tags = tagService.getAllTags();
                request.setAttribute("tags", tags);
                request.getRequestDispatcher("/views/Tag/tags.jsp").forward(request, response);
                break;

            case "edit":
                long id = Long.valueOf(request.getParameter("id"));
                Tag tag = tagService.findTagById(id).orElse(null);
                if(tag != null) {
                    request.setAttribute("tag", tag);
                    request.getRequestDispatcher("/views/Tag/editTag.jsp").forward(request, response);
                }else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Utilisateur non trouvé");
                }
                break;

            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "add":
                String name = request.getParameter("name");
                Tag newTag = new Tag();
                newTag.setName(name);
                tagService.addTag(newTag);
                response.sendRedirect("tags?action=getAll");
                break;
            case "delete":
                String tagIdStr = request.getParameter("id");
                if (tagIdStr == null) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Le paramètre 'id' est manquant.");
                    return;
                }
                long tagId = Long.parseLong(tagIdStr);
                tagService.deleteTag(tagId);
                response.sendRedirect("tags?action=getAll");
                break;
            case "update":
                long Id = Long.parseLong(request.getParameter("id"));
                String newName = request.getParameter("name");
                Tag tagToUpdate = new Tag();
                tagToUpdate.setId(Id);
                tagToUpdate.setName(newName);
                tagService.updateTag(tagToUpdate);
                response.sendRedirect("tags?action=getAll");
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                break;
        }
    }

}
