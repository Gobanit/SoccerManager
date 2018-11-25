package cz.fi.muni.pa165.soccermanager.service.facade;

import cz.fi.muni.pa165.soccermanager.api.dto.PlayerChangeDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.PlayerCreateDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.PlayerDTO;
import cz.fi.muni.pa165.soccermanager.api.dto.PlayerFreeDTO;
import cz.fi.muni.pa165.soccermanager.api.enums.Footed;
import cz.fi.muni.pa165.soccermanager.api.enums.Position;
import cz.fi.muni.pa165.soccermanager.api.facade.PlayerFacade;
import cz.fi.muni.pa165.soccermanager.data.SoccerPlayer;
import cz.fi.muni.pa165.soccermanager.service.BeanMapping;
import cz.fi.muni.pa165.soccermanager.service.PlayerService;
import org.hibernate.service.spi.ServiceException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * Unit tests for {@link cz.fi.muni.pa165.soccermanager.api.facade.PlayerFacade}.
 *
 * @author Lenka Horvathova
 */
public class PlayerFacadeTest {
    private PlayerFacade playerFacade;

    @Mock
    private PlayerService playerService;

    @Mock
    private BeanMapping beanMapping;

    @BeforeMethod
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
        playerFacade = new PlayerFacadeImpl(beanMapping, playerService);
    }

    @Test
    public void createPlayerTest() {
        PlayerCreateDTO mockPlayerDTO = Mockito.mock(PlayerCreateDTO.class);
        SoccerPlayer mockSoccerPlayer = Mockito.mock(SoccerPlayer.class);
        when(beanMapping.mapTo(mockPlayerDTO, SoccerPlayer.class)).thenReturn(mockSoccerPlayer);

        playerFacade.createPlayer(mockPlayerDTO);
        verify(playerService).createPlayer(mockSoccerPlayer);
    }

    @Test
    public void removePlayerTest() {
        SoccerPlayer mockSoccerPlayer = Mockito.mock(SoccerPlayer.class);
        when(playerService.findPlayerById(1L)).thenReturn(mockSoccerPlayer);

        playerFacade.removePlayer(1L);
        verify(playerService).removePlayer(mockSoccerPlayer);
    }

    @Test
    public void findPlayerByIdTest() {
        SoccerPlayer mockPlayer = Mockito.mock(SoccerPlayer.class);
        when(playerService.findPlayerById(1L)).thenReturn(mockPlayer);

        PlayerDTO mockDTO = Mockito.mock(PlayerDTO.class);
        when(beanMapping.mapTo(mockPlayer, PlayerDTO.class)).thenReturn(mockDTO);

        assertEquals(playerFacade.findPlayerById(1L), mockDTO);
        verify(playerService).findPlayerById(1L);
    }

    @Test
    public void findAllPlayersTest() {
        List<SoccerPlayer> mockPlayers = new ArrayList<>();
        when(playerService.findAllPlayers()).thenReturn(mockPlayers);

        List<PlayerDTO> mockDTOs = new ArrayList<>();
        when(beanMapping.mapTo(mockPlayers, PlayerDTO.class)).thenReturn(mockDTOs);

        assertEquals(playerFacade.findAllPlayers(), mockDTOs);
        verify(playerService).findAllPlayers();
    }

    @Test
    public void findFreePlayersTest() {
        List<SoccerPlayer> mockPlayers = new ArrayList<>();
        when(playerService.findFreePlayers()).thenReturn(mockPlayers);

        List<PlayerFreeDTO> mockDTOs = new ArrayList<>();
        when(beanMapping.mapTo(mockPlayers, PlayerFreeDTO.class)).thenReturn(mockDTOs);

        assertEquals(playerFacade.findFreePlayers(), mockDTOs);
        verify(playerService).findFreePlayers();
    }

    @Test
    public void changePlayerAttributesTest() {
        PlayerChangeDTO mockPlayerDTO = Mockito.mock(PlayerChangeDTO.class);
        when(mockPlayerDTO.getId()).thenReturn(1L);
        when(mockPlayerDTO.getPosition()).thenReturn(Position.DEFFENSE);
        when(mockPlayerDTO.getFooted()).thenReturn(Footed.LEFT);
        when(mockPlayerDTO.getRating()).thenReturn(7);

        playerFacade.changePlayerAttributes(mockPlayerDTO);
        verify(playerService).findPlayerById(1L);
    }
}
