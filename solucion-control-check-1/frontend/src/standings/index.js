import React, { useState } from "react";
import { Table } from "reactstrap";
import tokenService from "../services/token.service";
import useFetchState from "../util/useFetchState";

export default function StandingsListing(props) {
    const jwt = tokenService.getLocalAccessToken();
    const [message, setMessage] = useState(null);
    const [visible, setVisible] = useState(false);
    // null mientras se carga, para no mostrar "NOT ENOUGH MATCHES" antes de tiempo
    const [ratings] = useFetchState(
        null,
        `/api/v1/seasons/${props.seasonId}/standings`,
        jwt,
        setMessage,
        setVisible
    );

    if (ratings === null) {
        return visible && message ? <div>{message}</div> : null;
    }

    if (!Array.isArray(ratings) || ratings.length === 0) {
        return <h1>NOT ENOUGH MATCHES</h1>;
    }

    return (
        <Table>
            <thead>
                <tr>
                    <th>Player</th>
                    <th>ELO</th>
                    <th>Wins</th>
                    <th>Losses</th>
                    <th>Draws</th>
                    <th>Win %</th>
                </tr>
            </thead>
            <tbody>
                {ratings.map((r) => (
                    <tr key={r.id}>
                        <td>{r.player?.username}</td>
                        <td>{r.elo}</td>
                        <td>{r.wins}</td>
                        <td>{r.losses}</td>
                        <td>{r.draws}</td>
                        <td>{r.winRate}</td>
                    </tr>
                ))}
            </tbody>
        </Table>
    );
}
