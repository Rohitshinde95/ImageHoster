package ImageHoster.controller;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.service.CommentService;
import ImageHoster.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/image/{imageId}/{imageTitle}/comments", method = RequestMethod.POST)
    public String createComment(@RequestParam("comment") String comment, @PathVariable("imageId") Integer imageId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggeduser");
        Image image = imageService.getImage(imageId);
        List<Comment> comments = image.getComments();
        Comment newComment = new Comment();
        newComment.setUser(user);
        newComment.setImage(image);
        newComment.setText(comment);
        newComment.setCreatedDate(new Date());
        newComment = commentService.createComment(newComment);
        comments.add(newComment);
        image.setComments(comments);
        imageService.updateImage(image);

        model.addAttribute("image", image);
        model.addAttribute("tags", image.getTags());
        model.addAttribute("comments", comments);
        return "redirect:/images/" + image.getId() + "/" + image.getTitle();
    }
}
