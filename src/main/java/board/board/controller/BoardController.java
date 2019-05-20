package board.board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.board.model.Board;
import board.board.model.BoardFile;
import board.board.service.BoardService;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @RequestMapping(value="/jpa/board", method=RequestMethod.GET)
    public ModelAndView openBoardList(/*ModelMap model*/) throws Exception{
        ModelAndView mv = new ModelAndView("boardList");

        List<Board> list = boardService.selectBoardList();
        mv.addObject("list", list);

        return mv;
    }

    @RequestMapping(value="/jpa/board/write", method=RequestMethod.GET)
    public String openBoardWrite() throws Exception{
        return "boardWrite";
    }

    @RequestMapping(value="jpa/board/write", method=RequestMethod.POST)
    public String writeBoard(Board board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
        boardService.saveBoard(board, multipartHttpServletRequest);
        return "redirect:/jpa/board";
    }

    @RequestMapping(value="jpa/board/{boardidx}", method=RequestMethod.GET)
    public ModelAndView openBoardDetail(@PathVariable("boardidx") int boardidx, ModelMap model) throws Exception{
        ModelAndView mv = new ModelAndView("boardDetail");

        Board board = boardService.selectBoardDetail(boardidx);
        mv.addObject("board", board);

        return mv;
    }

    @RequestMapping(value="jpa/board/{boardidx}", method=RequestMethod.PUT)
    public String updateBoard(Board board) throws Exception{
        boardService.saveBoard(board,null);
        return "redirect:/jpa/board";
    }

    @RequestMapping(value="jpa/board/{boardidx}", method=RequestMethod.DELETE)
    public String deleteBoard(@PathVariable("boardidx") int boardidx) throws Exception{
        boardService.deleteBoard(boardidx);
        return "redirect:/jpa/board";
    }

    @RequestMapping(value="/jpa/board/file", method=RequestMethod.GET)
    public void downloadBoardFile(int boardidx, int idx, HttpServletResponse response) throws Exception{
        BoardFile file = boardService.selectBoardFileInformation(idx, boardidx);
        System.out.println("file : "+file);
        byte[] files = FileUtils.readFileToByteArray(new File(file.getStoredfilepath()));

        response.setContentType("application/octet-stream");
        response.setContentLength(files.length);
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(file.getOriginalfilename(),"UTF-8")+"\";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        response.getOutputStream().write(files);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}