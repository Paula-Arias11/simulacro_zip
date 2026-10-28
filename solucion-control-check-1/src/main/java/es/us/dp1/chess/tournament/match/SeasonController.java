package es.us.dp1.chess.tournament.match;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.us.dp1.chess.tournament.exceptions.ResourceNotFoundException;
import es.us.dp1.chess.tournament.user.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/seasons")
@SecurityRequirement(name = "bearerAuth")
public class SeasonController {

    UserService userService;
    SeasonService seasonService;


    public SeasonController(SeasonService service,UserService userService){
        this.seasonService=service;
        this.userService=userService;    
    }

    @GetMapping
    public List<Season> getAll() {
        return seasonService.getAll();
    }

    @GetMapping("/{id}/standings")
    public List<Rating> getStandings(@PathVariable("id") Integer id) {
        Season season = seasonService.getById(id);
        if (season == null) {
            throw new ResourceNotFoundException("Season", "id", id);
        }
        return seasonService.getStandings(season);
    }

}
