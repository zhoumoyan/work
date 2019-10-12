package site.zhongkai.ask.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/portal")
public class PortalController {

    @GetMapping("/index")
    public String getIndex(){
        return "index";
    }

	@GetMapping("/activity")
	public String getActivity(){
		return "html/activity";
	}

	@GetMapping("/answer_page")
	public String getAnswerPage(){
		return "html/answerPage";
	}

	@GetMapping("/exchange")
	public String getExchange(){
		return "html/exchange";
	}

	@GetMapping("/subject_page")
	public String getSubjectPage(){
		return "html/subjectPage";
	}

	@GetMapping("/tickets")
	public String getTickets(){
		return "html/tickets";
	}

}
